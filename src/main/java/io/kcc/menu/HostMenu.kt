package io.kcc.menu

import io.kcc.Configuration

/**
 * See the comments in the MainMenu class
 */
object HostMenu {

    fun translateUserInput(vararg args: String): () -> Unit {
        return when {
            args.isEmpty() -> HostMenu::readHost
            args[0] == "register" && args.size > 1 -> { -> registerHost(args[1]) }
            args[0] == "unregister" -> HostMenu::unregisterHost
            else -> HostMenu::printHelpMessage
        }
    }

    internal fun readHost() {
        try {
            val host = Configuration().load().readHost()
            println(host)
        } catch (e: IllegalStateException) {
            println("${e.message}\n")
            printHelpMessage()
        }
    }

    internal fun registerHost(hostUrl: String) {
        try {
            val host = Configuration().load().registerHost(hostUrl).store().readHost()
            println("The host ${host.url} has been registered.")
        } catch (e: Exception) {
            println("${e.message}\n")
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
            println("${e.message}\n")
            UserMenu.printHelpMessage()
        }
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc host
            kcc host register <url>
            kcc host unregister
            kcc host -h | --help
            
        options:
            -h --help   Show this screen
        """.trimIndent()
    )
}
