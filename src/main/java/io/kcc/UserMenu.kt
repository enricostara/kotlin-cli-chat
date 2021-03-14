package io.kcc

/**
 * See the comments in the MainMenu class
 */
object UserMenu {

    fun translateUserInput(vararg args: String): () -> Unit {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        return when {
            args.isEmpty() -> UserMenu::readUser
            args[0] == "delete" -> UserMenu::deleteUser
            // Create a lambda that will call the member function using a stored parameter.
            args[0] == "create" && args.size > 1 -> { -> createUser(args[1]) }
            args[0] == "rename" && args.size > 1 -> { -> renameUser(args[1]) }
            else -> UserMenu::printHelpMessage
        }
    }

    internal fun readUser() {
        try {
            val user = Configuration().load().readUser()
            println(user)
        } catch (e: IllegalStateException) {
            println("${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun createUser(name: String) {
        try {
            val user = Configuration().load().createUser(name).store().readUser()
            println("The user ${user.name} has been created.")
        } catch (e: Exception) {
            println("${e.message}\n")
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
            println("${e.message}\n")
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
            println("${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc user
            kcc user create <name>
            kcc user rename <name>
            kcc user delete
            kcc user -h | --help
            
        options:
            -h --help   Show this screen
        """.trimIndent()
    )
}
