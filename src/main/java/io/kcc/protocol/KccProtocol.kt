package io.kcc.protocol

import io.kcc.model.Host
import io.kcc.model.Message
import io.kcc.model.Topic
import io.kcc.model.User

interface KccProtocol {

    fun accept(requiredHost: Host): Boolean

    fun readTopics(): Set<Topic>
    fun createTopic(topic: Topic, user: User)
    fun joinTopic(topic: Topic, user: User)
    fun leaveTopic(topic: Topic, user: User)
    fun deleteTopic(topic: Topic, user: User)

    fun readMessages(topic: Topic, user: User, numOfMessage: Int): List<Message>
    fun writeMessage(message: Message)
}
