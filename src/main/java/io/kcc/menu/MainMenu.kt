package io.kcc.menu

import io.kcc.model.topicSeparator
import io.kcc.projectVersion

const val userMenuItem = "user"
const val hostMenuItem = "host"
const val topicMenuItem = "topic"
const val helpOption = "--help"
const val helpShortOption = "-h"
const val versionOption = "--version"

const val createMenuItem = "new"
const val deleteMenuItem = "del"

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 * [see](https://kotlinlang.org/docs/object-declarations.html)
 */
object MainMenu {

    /**
     * A variable number of arguments 'vararg' can also be used to receive a list of strings.
     * [see](https://kotlinlang.org/docs/functions.html#variable-number-of-arguments-varargs)
     *
     * It returns '() -> Unit', a function corresponding to the user input.
     * This 'Unit' type corresponds to the 'void' type in Java.
     * [see](https://kotlinlang.org/docs/functions.html#unit-returning-functions)
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
            topicMenuItem -> TopicMenu.translateUserInput(*(args.drop(1).toTypedArray()))
            // if-else is an expression and it is much more readable than the Java ternary operator
            else -> if (args[0].startsWith(topicSeparator)) MessageMenu.translateUserInput(*args) else MainMenu::printHelpMessage
        }
    }

    /**
     * A function body can be an expression.
     * The 'internal' visibility means that any code declared in the same module can access this element, such as test code.
     * [see](https://kotlinlang.org/docs/visibility-modifiers.html#classes-and-interfaces)
     */
    internal fun printHelpMessage() = println(
        // Triple-quoted strings gives you an easy way to embed in your programs text containing line breaks,
        // it fits perfectly in writing the help message.
        """ 
        usage:
            kcc $userMenuItem
            kcc $hostMenuItem
            kcc $topicMenuItem
            kcc <${topicSeparator}topic> <msg>
            kcc <${topicSeparator}topic>
            kcc <${topicSeparator}topic${topicSeparator}#>
            kcc <${topicSeparator}topic${topicSeparator}user>
            kcc <${topicSeparator}topic${topicSeparator}user${topicSeparator}#>
            kcc $helpShortOption | $helpOption
            kcc $versionOption
            
        options:
            $helpShortOption $helpOption            Show this screen
            $versionOption            Show version
        """.trimIndent()
    )

    /**
     *  String templates allow you to include variable references and expressions into strings.
     *  [see](https://kotlinlang.org/docs/basic-syntax.html#string-templates)
     */
    internal fun printVersion() = println("kotlin-cli-client v$projectVersion")
}
