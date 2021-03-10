package io.kcc

import java.lang.invoke.MethodHandles
import java.util.*

/**
 * The entry point of a Kotlin application is the main function.
 * Functions can be declared at the top level of a file, a class is not needed.
 *
 * String[] are represented by Array<String>
 */
fun main(args: Array<String>) {
    // One way used by Java to find the current class works with top-level functions as well
    val klass = MethodHandles.lookup().lookupClass()

    // the 'new' keyword is not needed to create an instance
    val properties = Properties()
    properties.load(klass.getResourceAsStream("/version.properties"))

    // String templates allow you to include variable references and expressions into strings.
    println("kotlin-cli-client v${properties.getProperty("version")}")

    // Here use the old if-else instead of the new 'when'
    if (args.isNotEmpty()) {
        // use the spread operator '*' to pass arguments one by one by calling a vararg function with an array
        processProgramArgs(*args)
    } else {
        printHelpMessage()
    }
}

/**
 * A variable number of arguments "vararg" can also be used to receive a list of strings
 */
fun processProgramArgs(vararg args: String) {
    for ((index, arg) in args.withIndex()) {
        println("$index - $arg")
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
