package io.kcc

object UserMenu {

    fun translateUserInput(vararg args: String): () -> Unit {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        return when {
            args.isEmpty() -> UserMenu::readUser
            args[0] == "delete" -> UserMenu::deleteUser
            // Create a lambda that will call the member function using a stored parameter.
            args[0] == "add" && args.size > 1 -> { -> addUser(args[1]) }
            args[0] == "rename" && args.size > 1 -> { -> renameUser(args[1]) }
            else -> UserMenu::printHelpMessage
        }
    }

    internal fun readUser() {
        try {
            val user = Configuration().load().readUser()
            println(
                """
                |User name: ${user.name}                
                |User topics: ${user.topics.joinToString("\n|  - ", "\n|  - ", "") { it.name }}
            """.trimMargin()
            )
        } catch (e: IllegalStateException) {
            println("User not yet defined.\n")
            printHelpMessage()
        }
    }

    internal fun addUser(name: String?) {
        println("Add user: $name")
    }

    internal fun renameUser(newName: String?) {
        println("rename user: $newName")
    }

    internal fun deleteUser() {
        println("Delete user")
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc user
            kcc user add <name>
            kcc user rename <name>
            kcc user delete
            kcc user -h | --help
            
        Options:
            -h --help   Show this screen
        """.trimIndent()
    )
}
