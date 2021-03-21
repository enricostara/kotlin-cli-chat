package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage
import io.kcc.model.Message
import io.kcc.model.Topic
import io.kcc.protocol.provideKccProtocolHandler

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 * [see](https://kotlinlang.org/docs/object-declarations.html)
 */
object MessageMenu {

    /**
     * A variable number of arguments 'vararg' can also be used to receive a list of strings.
     * [see](https://kotlinlang.org/docs/functions.html#variable-number-of-arguments-varargs)
     *
     * It returns '() -> Unit', a function corresponding to the user input.
     * This 'Unit' type corresponds to the 'void' type in Java.
     * [see](https://kotlinlang.org/docs/functions.html#unit-returning-functions)
     */
    fun translateUserInput(vararg args: String): () -> Unit = when {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        args.size > 1 -> { -> sendMessage(args[0].drop(1), *(args.drop(1)).toTypedArray()) }
        else -> { -> readMessages(args[0].drop(1)) }
    }

    internal fun readMessages(topicName: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val topic = Topic(topicName)
                require(user.joinedTopic(topic)) { "user ${user.name} has to join the topic $topic before reading messages!" }
                val protocol = provideKccProtocolHandler(readHost())
                val messages = protocol.readMessages(topicName)
                println(
                    when {
                        messages.isEmpty() -> "$topicName: no messages"
                        else -> messages.joinToString("\n")
                    }
                )
            } catch (e: IllegalArgumentException) {
                println("$errorMessage${e.message}\n")
                MainMenu.printHelpMessage()
            }
        }
    }

    internal fun sendMessage(topicName: String, vararg words: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val topic = Topic(topicName)
                require(user.joinedTopic(topic)) { "user ${user.name} has to join the topic $topic before sending a message!" }
                val protocol = provideKccProtocolHandler(readHost())
                val msg = Message(topic, user.name.toString(), words.joinToString(" "))
                protocol.sendMessage(msg)
                val messages = protocol.readMessages(topicName, 3)
                println(messages.joinToString("\n"))
            } catch (e: IllegalArgumentException) {
                println("$errorMessage${e.message}\n")
                MainMenu.printHelpMessage()
            }
        }
    }
}
