package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage
import io.kcc.model.Topic
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

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun readTopics() {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val protocol = provideKccProtocolHandler(readHost())
                val topics = protocol.readTopics()
                user.validateTopics(topics)
                updateUser(user).store()
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
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun createTopic(name: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val protocol = provideKccProtocolHandler(readHost())
                val topic = Topic(name, user)
                protocol.createTopic(topic)
                println("topic $topic has been created.")
            } catch (e: Exception) {
                println("$errorMessage${e.message}\n")
                printHelpMessage()
            }
        }
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun joinTopic(name: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val protocol = provideKccProtocolHandler(readHost())
                val topic = Topic(name)
                protocol.joinTopic(topic)
                user.validateTopics(protocol.readTopics())
                user.joinTopic(topic)
                updateUser(user).store()
                println("topic $topic has been joined.")
            } catch (e: Exception) {
                println("$errorMessage${e.message}\n")
                printHelpMessage()
            }
        }
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun leaveTopic(name: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val protocol = provideKccProtocolHandler(readHost())
                user.validateTopics(protocol.readTopics())
                updateUser(user)
                val topic = Topic(name)
                protocol.leaveTopic(topic)
                user.leaveTopic(topic)
                updateUser(user)
                println("topic $topic has been left.")
            } catch (e: Exception) {
                println("$errorMessage${e.message}\n")
                printHelpMessage()
            } finally {
                store()
            }
        }
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun deleteTopic(name: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val protocol = provideKccProtocolHandler(readHost())
                user.validateTopics(protocol.readTopics())
                updateUser(user)
                val topic = Topic(name, user)
                protocol.deleteTopic(topic)
                user.leaveTopic(topic)
                updateUser(user)
                println("topic $topic has been deleted.")
            } catch (e: Exception) {
                println("$errorMessage${e.message}\n")
                printHelpMessage()
            } finally {
                store()
            }
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
