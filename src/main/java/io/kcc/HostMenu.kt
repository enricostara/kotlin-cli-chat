package io.kcc

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

    internal fun registerHost(hostAddress: String) {
        println("register host $hostAddress")
    }

    internal fun unregisterHost() {
        println("unregister host")
    }

    internal fun printHelpMessage() = println(
        """ 
        usage:
            kcc host
            kcc host register <name>
            kcc host unregister
            kcc host -h | --help
            
        Options:
            -h --help   Show this screen
        """.trimIndent()
    )
}
