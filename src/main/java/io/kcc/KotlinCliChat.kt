package io.kcc

import java.lang.invoke.MethodHandles
import java.util.*

/**
 *  Top level variable to store the project version.
 *  'lateinit' allows initializing a not null variable outside of a constructor
 */
lateinit var projectVersion: String

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
    projectVersion = properties.getProperty("version")

    // use the spread operator '*' to pass input arguments one by one by calling a vararg function with an array
    val action = MainMenu.translateUserInput(*args)

    // execute the action required by the user
    action()
}
