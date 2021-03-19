package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage
import io.kcc.model.User

const val renameMenuItem = "ren"

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 * [see](https://kotlinlang.org/docs/object-declarations.html)
 */
object UserMenu {

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
        args.isEmpty() -> UserMenu::readUser
        args[0] == deleteMenuItem -> UserMenu::deleteUser
        // Create a lambda that will call the member function using a stored parameter.
        args[0] == createMenuItem && args.size > 1 -> { -> createUser(args[1]) }
        args[0] == renameMenuItem && args.size > 1 -> { -> renameUser(args[1]) }
        else -> UserMenu::printHelpMessage
    }

    internal fun readUser() {
        try {
            val user = Configuration().load().readUser()
            println(user)
        } catch (e: IllegalStateException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun createUser(name: String) {
        try {
            val user = Configuration().load().createUser(name).store().readUser()
            println("user ${user.name} has been created.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun renameUser(newName: String) {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                val oldName = user.name.toString()
                user.name = User.Name(newName)
                updateUser(user).store()
                println("user $oldName is now known as ${user.name}")
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
    internal fun deleteUser() {
        Configuration().apply {
            try {
                load()
                val user = readUser()
                deleteUser().store()
                println("user ${user.name} has been deleted.")
            } catch (e: IllegalStateException) {
                println("$errorMessage${e.message}\n")
                printHelpMessage()
            }
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc $userMenuItem
            kcc $userMenuItem $createMenuItem <name>
            kcc $userMenuItem $renameMenuItem <name>
            kcc $userMenuItem $deleteMenuItem
            kcc $userMenuItem $helpShortOption | $helpOption
            
        options:
            $helpShortOption $helpOption   Show this screen
        """.trimIndent()
    )
}
