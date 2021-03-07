package io.kcc

import java.lang.invoke.MethodHandles
import java.util.*

/**
 * In Kotlin functions can be declared at the top level of a file, a class is not needed.
 * The entry point of a Kotlin application is the main function.
 */
fun main() {
    // One way used by Java to find the current class works with top-level functions
    val klass = MethodHandles.lookup().lookupClass()

    val properties = Properties()
    properties.load(klass.getResourceAsStream("/version.properties"))

    println("kotlin-cli-client v${properties.getProperty("version")}")
}