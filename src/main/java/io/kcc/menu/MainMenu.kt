package io.kcc.menu

import io.kcc.projectVersion

const val userMenuItem = "user"
const val hostMenuItem = "host"
const val helpOption = "--help"
const val helpShortOption = "-h"
const val versionOption = "--version"
const val messageShortOption = "-m"
const val printShortOption = "-p"

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
        // 'when' with argument is more like Java's 'switch-case' statement.
        return when (args[0]) {
            // Use '::' to get e member reference
            helpOption, helpShortOption -> MainMenu::printHelpMessage
            versionOption -> MainMenu::printVersion
            // with 'drop(1)' it will pass all arguments except the first (already used)
            userMenuItem -> UserMenu.translateUserInput(*(args.drop(1).toTypedArray()))
            hostMenuItem -> HostMenu.translateUserInput(*(args.drop(1).toTypedArray()))
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
            kcc $userMenuItem
            kcc $hostMenuItem
            kcc $messageShortOption <msg>
            kcc $printShortOption <number_of_msg>
            kcc $helpShortOption | $helpOption
            kcc $versionOption
            
        options:
            $messageShortOption <msg>    Send a message to the topic
            $printShortOption <n>      Print <n> number of topic's messages, 0 means all
            $helpShortOption $helpOption   Show this screen
            $versionOption   Show version
        """.trimIndent()
    )

    /**
     *  String templates allow you to include variable references and expressions into strings.
     */
    internal fun printVersion() = println("kotlin-cli-client v$projectVersion")
}
