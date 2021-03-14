package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage

const val createMenuItem = "new"
const val renameMenuItem = "ren"
const val deleteMenuItem = "del"

/**
 * See the comments in the MainMenu class
 */
object UserMenu {

    fun translateUserInput(vararg args: String): () -> Unit {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        return when {
            args.isEmpty() -> UserMenu::readUser
            args[0] == deleteMenuItem -> UserMenu::deleteUser
            // Create a lambda that will call the member function using a stored parameter.
            args[0] == createMenuItem && args.size > 1 -> { -> createUser(args[1]) }
            args[0] == renameMenuItem && args.size > 1 -> { -> renameUser(args[1]) }
            else -> UserMenu::printHelpMessage
        }
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
            println("The user ${user.name} has been created.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun renameUser(newName: String) {
        try {
            val configuration = Configuration().load()
            val user = configuration.readUser()
            val oldName = user.name.toString()
            user.name.value = newName
            configuration.updateUser(user).store().readUser()
            println("The user $oldName is now known as ${user.name}")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun deleteUser() {
        try {
            val configuration = Configuration().load()
            val user = configuration.readUser()
            configuration.deleteUser().store()
            println("The user ${user.name} has been deleted.")
        } catch (e: IllegalStateException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
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