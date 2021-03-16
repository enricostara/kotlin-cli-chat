package io.kcc.menu

import io.kcc.errorMessage

const val joinMenuItem = "join"
const val leaveMenuItem = "leave"

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 */
object TopicMenu {

    /**
     * A variable number of arguments 'vararg' can also be used to receive a list of strings.
     * It returns '() -> Unit', a function corresponding to the user input.
     * This 'Unit' type corresponds to the 'void' type in Java.
     */
    fun translateUserInput(vararg args: String): () -> Unit = when {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        args.isEmpty() -> TopicMenu::readTopics
        // Create a lambda that will call the member function using a stored parameter.
        args[0] == createMenuItem && args.size > 1 -> { -> createTopic(args[1]) }
        args[0] == joinMenuItem && args.size > 1 -> { -> joinTopic(args[1]) }
        args[0] == leaveMenuItem && args.size > 1 -> { -> leaveTopic(args[1]) }
        else -> TopicMenu::printHelpMessage
    }

    internal fun readTopics() {
        try {
            println("No topics")
//            val user = Configuration().load().readUser()
//            println(user)
        } catch (e: IllegalStateException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun createTopic(name: String) {
        try {
            println("Create topic $name")
//            val user = Configuration().load().createUser(name).store().readUser()
//            println("The user ${user.name} has been created.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun joinTopic(name: String) {
        try {
            println("Join topic $name")
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
