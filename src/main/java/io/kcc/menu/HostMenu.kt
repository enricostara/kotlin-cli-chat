package io.kcc.menu

import io.kcc.Configuration
import io.kcc.errorMessage

const val registerMenuItem = "reg"
const val unregisterMenuItem = "unreg"

/**
 * Use 'object' to declare a singleton, a class for which you need only one instance.
 */
object HostMenu {

    /**
     * A variable number of arguments 'vararg' can also be used to receive a list of strings.
     * It returns '() -> Unit', a function corresponding to the user input.
     * This 'Unit' type corresponds to the 'void' type in Java.
     */
    fun translateUserInput(vararg args: String): () -> Unit = when {
        // Use 'when' without an argument to manage more complex cases by providing Boolean expressions.
        args.isEmpty() -> HostMenu::readHost
        args[0] == registerMenuItem && args.size > 1 -> { -> registerHost(args[1]) }
        args[0] == unregisterMenuItem -> HostMenu::unregisterHost
        else -> HostMenu::printHelpMessage
    }


    internal fun readHost() {
        try {
            val host = Configuration().load().readHost()
            println(host)
        } catch (e: IllegalStateException) {
            println("$errorMessage${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun registerHost(hostUrl: String) {
        try {
            val host = Configuration().load().registerHost(hostUrl).store().readHost()
            println("The host ${host.url} has been registered.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            UserMenu.printHelpMessage()
        }
    }

    internal fun unregisterHost() {
        try {
            val configuration = Configuration().load()
            val host = configuration.readHost()
            configuration.unregisterHost().store()
            println("The host ${host.url} has been unregistered.")
        } catch (e: Exception) {
            println("$errorMessage${e.message}\n")
            UserMenu.printHelpMessage()
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc $hostMenuItem
            kcc $hostMenuItem $registerMenuItem <url>
            kcc $hostMenuItem $unregisterMenuItem
            kcc $hostMenuItem $helpShortOption | $helpOption
            
        options:
            $helpShortOption $helpOption   Show this screen
        """.trimIndent()
    )
}
