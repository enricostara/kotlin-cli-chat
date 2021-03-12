package io.kcc

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 */
object MainMenu {

    /**
     * A variable number of arguments 'vararg' can also be used to receive a list of strings.
     * It returns '() -> Unit', a function corresponding to the user input.
     * This 'Unit' type corresponds to the 'void' type in Java.
     */
    fun translateUserInput(vararg args: String): () -> Unit {
        if (args.isEmpty()) {
            return MainMenu::printHelpMessage
        }
        // 'when' is an expression and it is an advanced form of the Java 'switch-case' statement.
        return when (args[0]) {
            // Use '::' to get e member reference
            "-h", "--help" -> MainMenu::printHelpMessage
            "--version" -> MainMenu::printVersion
            "user" -> UserMenu.translateUserInput(*args.drop(1).toTypedArray())
            else -> MainMenu::printHelpMessage
        }
    }

    /**
     * A function body can be an expression.
     * 'internal' visibility means that any code declared in the same module can access this element, such as test code.
     */
    internal fun printHelpMessage() = println(
        // Triple-quoted strings gives you an easy way to embed in your programs text containing line breaks,
        // it fits perfectly in writing the help message.
        """ 
        usage:
            kcc user
            kcc -h | --help
            kcc --version
            
        Options:
            -h --help   Show this screen
            --version   Show version
        """.trimIndent()
    )

    /**
     *  String templates allow you to include variable references and expressions into strings.
     */
    internal fun printVersion() = println("kotlin-cli-client v$projectVersion")
}
