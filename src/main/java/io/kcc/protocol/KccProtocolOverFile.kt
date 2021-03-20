package io.kcc.protocol

import io.kcc.model.*
import java.io.File

object KccProtocolOverFile : KccProtocol {

    const val messageDelimiter: Char = '|'

    lateinit var host: Host

    override fun accept(requiredHost: Host): Boolean = when (requiredHost.url.protocol) {
        "file" -> {
            host = requiredHost
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
        if (!topics.contains(topic)) error("cannot read topic $topic', the topic doesn't even exist!")
        return topics.apply { retainAll(listOf(topic)) }.first()
    }

    /**
     * The topic.owner could be null, then the safe call operator ?. has been used to get the owner's name
     * [see](https://kotlinlang.org/docs/null-safety.html#safe-calls)
     */
    override fun createTopic(topic: Topic) {
        val topics = readTopics()
        // use 'error' that throws an IllegalStateException with the given message
        if (topics.contains(topic)) error("topic $topic already exists!")
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
        if (!topics.contains(topic)) error("cannot delete topic $topic, doesn't even exist!")
        val file = File(retrieveTopicFilePath(topic))
        if (!file.exists()) error("user ${topic.owner?.name} cannot delete topic $topic")
        file.delete()
    }

    override fun readMessages(topicName: String): List<Message> {
        val topic = readTopic(topicName)
        val topicFile = File(retrieveTopicFilePath(topic))
        return topicFile.readLines().map {
            Message(topic, it.substringBefore(messageDelimiter), it.substringAfter(messageDelimiter))
        }
    }

    override fun writeMessage(message: Message) {
        TODO("Not yet implemented")
    }

    private fun retrieveTopicFilePath(topic: Topic) =
        "${host.url.path}${File.separator}.${topic.name}${topic.owner?.name}.kcc"
}