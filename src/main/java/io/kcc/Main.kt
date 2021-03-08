package io.kcc

import java.lang.invoke.MethodHandles
import java.util.*

/**
 * In Kotlin functions can be declared at the top level of a file, a class is not needed.
 * The entry point of a Kotlin application is the main function.
 *
 * String[] in Kotlin is represented by Array<String>
 */
fun main(args: Array<String>) {
    // One way used by Java to find the current class works with top-level functions
    val klass = MethodHandles.lookup().lookupClass()

    val properties = Properties()
    properties.load(klass.getResourceAsStream("/version.properties"))

    // String templates allow you to include variable references and expressions into strings.
    println("kotlin-cli-client v${properties.getProperty("version")}")

    if (args.isNotEmpty()) {
        // use the spread operator '*' to pass arguments one by one by calling a vararg function with an array
        processProgramArgs(*args)
    } else {
        printHelpMessage()
    }
}


/**
 * A function body can be an expression
 *
 * Triple-quoted strings gives you an easy way to embed in your programs text containing line breaks,
 * it fits perfectly in writing the help message
 */
fun printHelpMessage() = println(
    """
            
        usage:
            kcc <username>
            
        Options:
            -h --help       Show this screen
            -v --version    Show version
        """.trimIndent()
)

/**
 * A variable number of arguments "vararg" can also be used to receive a list of strings
 */
fun processProgramArgs(vararg args: String) {
    for ((index, arg) in args.withIndex()) {
        println("$index - $arg")
    }
}