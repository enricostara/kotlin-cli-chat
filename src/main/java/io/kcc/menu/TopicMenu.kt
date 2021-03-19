package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage
import io.kcc.model.Topic
import io.kcc.protocol.KccProtocol
import io.kcc.protocol.provideKccProtocolHandler

const val joinMenuItem = "join"
const val leaveMenuItem = "leave"

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 * [see](https://kotlinlang.org/docs/object-declarations.html)
 */
object TopicMenu {

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
        args.isEmpty() -> TopicMenu::readTopics
        // Create a lambda that will call the member function using a stored parameter.
        args[0] == createMenuItem && args.size > 1 -> { -> createTopic(args[1]) }
        args[0] == joinMenuItem && args.size > 1 -> { -> joinTopic(args[1]) }
        args[0] == leaveMenuItem && args.size > 1 -> { -> leaveTopic(args[1]) }
        args[0] == deleteMenuItem && args.size > 1 -> { -> deleteTopic(args[1]) }
        else -> TopicMenu::printHelpMessage
    }

    private fun getProtocol(): KccProtocol {
        val host = Configuration().load().readHost()
        return provideKccProtocolHandler(host)
    }

    internal fun readTopics() {
        try {
            val user = Configuration().load().readUser()
            val protocol = getProtocol()
            val topics = protocol.readTopics()
            println("""
                |topics: ${
                when {
                    topics.isEmpty() -> "no /topics"
                    else -> topics.joinToString("\n|    - ", "\n|    - ", "") {
                        // if-else is an expression and it is much more readable than the Java ternary operator 
                        "$it${if (user.topics.contains(it)) "\t*" else ""}"
                    }
                }
            }""".trimMargin())
        } catch (e: IllegalStateException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun createTopic(name: String) {
        try {
            val user = Configuration().load().readUser()
            val protocol = getProtocol()
            val topic = Topic(name, user)
            protocol.createTopic(topic)
            println("topic $topic has been created.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun joinTopic(name: String) {
        try {
            val user = Configuration().load().readUser()
            val protocol = getProtocol()
            val topic = Topic(name)
            protocol.joinTopic(topic, user)
            user.joinTopic(topic)
            Configuration().load().updateUser(user).store()
            println("topic $topic has been joined.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun leaveTopic(name: String) {
        try {
            println("Leave topic $name")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun deleteTopic(name: String) {
        try {
            println("Delete topic $name")
//            val user = Configuration().load().createUser(name).store().readUser()
//            println("The user ${user.name} has been created.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc $topicMenuItem
            kcc $topicMenuItem $createMenuItem <name>
            kcc $topicMenuItem $joinMenuItem <name>
            kcc $topicMenuItem $leaveMenuItem <name>
            kcc $topicMenuItem $helpShortOption | $helpOption
            
        options:
            $helpShortOption $helpOption   Show this screen
        """.trimIndent()
    )
}
