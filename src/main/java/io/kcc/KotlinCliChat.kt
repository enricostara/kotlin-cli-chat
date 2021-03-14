package io.kcc

import io.kcc.menu.MainMenu
import java.lang.invoke.MethodHandles
import java.util.*

/**
 *  'var' is like a general variable and can be assigned multiple times.
 *  'lateinit' allows initializing a not null variable outside of a constructor.
 *   Variables can be declared at the top level of a file, a class is not needed.
 *   The declaration begins with the variable name and then with the variable type.
 */
lateinit var projectVersion: String

/**
 * The entry point of a Kotlin application is the main function.
 * Functions can be declared at the top level of a file, a class is not needed.
 *
 * String[] are represented by Array<String>
 */
fun main(args: Array<String>) {

    // 'val' is a immutable variable and can NOT be assigned multiple times and can be initialized only single time.
    // One way used by Java to find the current class works inside top-level functions as well.
    // The type of variable, if omitted, can be inferred by the compiler.
    val klass = MethodHandles.lookup().lookupClass()

    // The "new" keyword is not defined and is not used to create an instance.
    val properties = Properties()
    properties.load(klass.getResourceAsStream("/version.properties"))
    projectVersion = properties.getProperty("version")

    // 'MainMenu' is the single instance (singleton) of the MainMenu class declared as 'object'.
    // Use the spread operator '*' to pass input arguments one by one by calling a vararg function with an array.
    val action = MainMenu.translateUserInput(*args)

    // Execute the action required by the user.
    action()
}
