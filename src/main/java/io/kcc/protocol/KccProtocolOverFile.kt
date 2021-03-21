package io.kcc.protocol

import io.kcc.model.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

object KccProtocolOverFile : KccProtocol {

    const val messageDelimiter: Char = '|'

    lateinit var host: Host

    override fun accept(requiredHost: Host): Boolean = when (requiredHost.url.protocol) {
        "file" -> {
            host = requiredHost
            require(File(host.url.path).exists()) { "cannot accept path ${host.url.path}, it doesn't exist!" }
            true
        }
        else -> false
    }

    /**
     * The 'substring*' String extension function at the rescue
     * [see](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/)
     */
    override fun readTopics(): Set<Topic> =
        File(host.url.path).walk()
            .filter { it.extension == "kcc" }
            .map {
                Topic(
                    it.name.substringAfter('.').substringBefore(userSymbol),
                    User(User.Name(it.name.substringAfter(userSymbol).substringBeforeLast('.')))
                )
            }
            .toSet()

    internal fun readTopic(topicName: String): Topic {
        val topic = Topic(topicName)
        val topics = readTopics().toMutableList()
        // 'require' throws an IllegalArgumentException with the result of calling lambda if the condition is false.
        require(topics.contains(topic)) { "cannot access topic $topic, it doesn't exist!" }
        return topics.apply { retainAll(listOf(topic)) }.first()
    }

    /**
     * The topic.owner could be null, then the safe call operator ?. has been used to get the owner's name
     * [see](https://kotlinlang.org/docs/null-safety.html#safe-calls)
     */
    override fun createTopic(topic: Topic) {
        val topics = readTopics()
        // 'require' throws an IllegalArgumentException with the result of calling lambda if the condition is false.
        require(!topics.contains(topic)) { "topic $topic already exists!" }
        File(retrieveTopicFilePath(topic)).createNewFile()
    }

    override fun joinTopic(topicName: String) {
        readTopic(topicName)
    }

    override fun leaveTopic(topicName: String) {
        readTopic(topicName)
    }

    override fun deleteTopic(topic: Topic) {
        val topics = readTopics()
        // 'require' throws an IllegalArgumentException with the result of calling lambda if the condition is false.
        require(topics.contains(topic)) { "cannot delete topic $topic, it doesn't even exist!" }
        val file = File(retrieveTopicFilePath(topic))
        require(file.exists()) { "user ${topic.owner?.name} cannot delete topic $topic" }
        file.delete()
    }

    override fun readMessages(topicName: String, takeLast: Int): List<Message> {
        val topic = readTopic(topicName)
        val topicFile = File(retrieveTopicFilePath(topic))
        val messages = topicFile.readLines().map {
            Message(topic, it.substringBefore(messageDelimiter), it.substringAfter(messageDelimiter))
        }
        val takeLastRows = if (takeLast == 0) messages.size else takeLast
        // 'takeLast' returns a list containing last [n] elements.
        return messages.takeLast(takeLastRows)
    }

    override fun sendMessage(message: Message) {
        val buffer = ByteBuffer.wrap("${message.userName}$messageDelimiter${message.content}\n".toByteArray())
        val topic = readTopic(message.topic.name)
        val path = Paths.get(retrieveTopicFilePath(topic))
        val channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND)
        // acquire an exclusive lock over the file associated with the given channel
        channel.lock()
        // set position at the end of file
        channel.position(channel.size())
        channel.write(buffer)
        // close the channel releasing the lock
        channel.close()
    }

    private fun retrieveTopicFilePath(topic: Topic) =
        "${host.url.path}${File.separator}.${topic.name}${topic.owner?.name}.kcc"
}
