package io.kcc

object UserMenu {

    fun translateUserInput(vararg args: String): () -> Unit {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        return when {
            args.isEmpty() -> UserMenu::printHelpMessage
            // Create a lambda that will call the member function using a stored parameter.
            args[0] == "add" && args.size > 1 -> { -> addUser(args[1]) }
            args[0] == "delete" -> UserMenu::deleteUser
            else -> UserMenu::printHelpMessage
        }
    }

    internal fun addUser(name: String?): String {
        println("Add user: $name")
        return "+$name"
    }

    internal fun deleteUser() {
        println("Delete user")
    }


    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc user add <name>
            kcc user remove
            kcc user -h | --help
            
        Options:
            -h --help   Show this screen
        """.trimIndent()
    )
}
