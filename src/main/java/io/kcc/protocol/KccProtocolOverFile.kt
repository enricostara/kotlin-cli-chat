package io.kcc.protocol

import io.kcc.model.Host
import io.kcc.model.Message
import io.kcc.model.Topic
import io.kcc.model.User
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

    override fun readTopics(): Set<Topic> =
        File(host.url.path).walk()
            .filter { it.extension == "kcc" }
            .map { Topic(it.name.substringAfter('.').substringBeforeLast('.')) }
            .toSet()


    override fun createTopic(topic: Topic, user: User) {
        TODO("Not yet implemented")
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