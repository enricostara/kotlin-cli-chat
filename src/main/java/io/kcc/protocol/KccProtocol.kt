package io.kcc.protocol

import io.kcc.model.Host
import io.kcc.model.Message
import io.kcc.model.Topic

/**
 * Interfaces in Kotlin can contain declarations of abstract methods, as well as method implementations.
 * What makes them different from abstract classes is that interfaces cannot store state.
 * They can have properties but these need to be abstract or to provide accessor implementations.
 * [see](https://kotlinlang.org/docs/interfaces.html)
 */
interface KccProtocol {

    fun accept(requiredHost: Host): Boolean

    fun readTopics(): Set<Topic>
    fun createTopic(topic: Topic)
    fun joinTopic(topicName: String)
    fun leaveTopic(topicName: String)
    fun deleteTopic(topic: Topic)

    fun readMessages(topicName: String): List<Message>
    fun writeMessage(message: Message)
}
