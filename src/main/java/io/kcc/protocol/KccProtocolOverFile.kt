package io.kcc.protocol

import io.kcc.model.*
import java.io.File

object KccProtocolOverFile : KccProtocol {

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
            .map { Topic(it.name.substringAfter('.').substringBefore(userSymbol)) }
            .toSet()


    /**
     * The topic.owner could be null, then the safe call operator ?. has been used to get the owner's name
     * [see](https://kotlinlang.org/docs/null-safety.html#safe-calls)
     */
    override fun createTopic(topic: Topic) {
        val topics = readTopics()
        // use 'error' that throws an IllegalStateException with the given message
        if (topics.contains(topic)) error("Topic $topic already exists!")
        File("${host.url.path}${File.separator}.${topic.name}${topic.owner?.name}.kcc").createNewFile()
    }

    override fun joinTopic(topic: Topic, user: User) {
        TODO("Not yet implemented")
    }

    override fun leaveTopic(topic: Topic, user: User) {
        TODO("Not yet implemented")
    }

    override fun deleteTopic(topic: Topic, user: User) {
        TODO("Not yet implemented")
    }

    override fun readMessages(topic: Topic, user: User, numOfMessage: Int): List<Message> {
        TODO("Not yet implemented")
    }

    override fun writeMessage(message: Message) {
        TODO("Not yet implemented")
    }
}