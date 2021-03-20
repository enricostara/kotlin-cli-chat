package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage
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
        args.size > 1 -> { -> sendMessage(args[0], args[1]) }
        else -> { -> readMessages(args[0]) }
    }

    internal fun readMessages(topic: String) {
        Configuration().apply {
            try {
                load()
                val protocol = provideKccProtocolHandler(readHost())
                val messages = protocol.readMessages(topic.drop(1))
                println(
                    when {
                        messages.isEmpty() -> "$topic: no messages"
                        else -> messages.joinToString("\n")
                    }
                )
            } catch (e: IllegalStateException) {
                println("$errorMessage${e.message}\n")
                TopicMenu.printHelpMessage()
            }
        }
    }

    internal fun sendMessage(topic: String, message: String) {
        try {
            println("send msg '$message' to topic $topic")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            MainMenu.printHelpMessage()
        }
    }
}
