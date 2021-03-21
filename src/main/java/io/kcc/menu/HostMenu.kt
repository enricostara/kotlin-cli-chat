package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 * [see](https://kotlinlang.org/docs/object-declarations.html)
 */
object HostMenu {

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
        args.isEmpty() -> HostMenu::readHost
        args[0] == createMenuItem && args.size > 1 -> { -> registerHost(args[1]) }
        args[0] == deleteMenuItem -> HostMenu::unregisterHost
        else -> HostMenu::printHelpMessage
    }


    internal fun readHost() {
        try {
            val host = Configuration().load().readHost()
            println(host)
        } catch (e: IllegalArgumentException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun registerHost(hostUrl: String) {
        try {
            val host = Configuration().load().registerHost(hostUrl).store().readHost()
            println("host ${host.url} has been registered.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    /**
     * The 'apply' extension here calls the specified function block with 'this: Configuration' value as its receiver.
     * [see](https://kotlinlang.org/docs/scope-functions.html#apply)
     */
    internal fun unregisterHost() {
        Configuration().apply {
            try {
                load()
                val host = readHost()
                unregisterHost().store()
                println("host ${host.url} has been unregistered.")
            } catch (e: IllegalArgumentException) {
                println("$errorMessage${e.message}\n")
                UserMenu.printHelpMessage()
            }
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc $hostMenuItem
            kcc $hostMenuItem $createMenuItem <url>
            kcc $hostMenuItem $deleteMenuItem
            kcc $hostMenuItem $helpShortOption | $helpOption
            
        options:
            $helpShortOption $helpOption   Show this screen
        """.trimIndent()
    )
}
