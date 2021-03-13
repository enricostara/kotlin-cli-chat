package io.kcc

import java.io.File
import java.util.*

/**
 * 'const' is much like using the 'static' keyword in Java.
 *  Compiler inlines the 'const val' values where they are used.
 *  This inlining is much more efficient than getting a static value from a class.
 */
const val fileName = ".kcc"
const val userHome = "user.home"
const val userName = "user.name"
const val userTopics = "user.topics"

/**
 * 'path' is a read-only property of the configuration class.
 * It is initialized in the constructor and can be accessed through the corresponding getter.
 * It has a default value in the declaration which avoid creating constructor overload.
 */
class Configuration(val path: String = System.getProperty(userHome)) {

    /**
     * 'hashMapOf()' without arguments returns an empty HashMap.
     * 'configMap' is the backing property for the 'configView' immutable map
     */
    private val configMap = hashMapOf<String, String>()
    val configView: Map<String, String>
        get() = configMap.toMap()

    fun load(): Configuration {
        val configFile = File("$path${File.separator}$fileName")
        if (configFile.exists()) {
            val config = Properties()
            config.load(configFile.inputStream())
            // Copy from Properties to HashMap using '.forEach', which accepts a lambda as an action.
            // 'map.put()' has been converted in an assignment.
            config.forEach { key, value -> configMap[key.toString()] = value.toString() }
        }
        return this
    }

    /**
     * the 'config' parameter in the function declaration has the 'configMap' property as a default value to provide
     * the standard use of the method with no arguments and to support unit tests in isolation by receiving a test configuration map.
     */
    fun readUser(config: Map<String, String> = configMap): User {
        // 'checkNotNull' throws an IllegalStateException if the value is null. Otherwise returns the not null value.
        val username = checkNotNull(config[userName]) { "username not found" }
        // Here make the type explicit for readability.
        val topics: List<Topic> = when (val topicsProp = config[userTopics]) {
            // 'listOf()' without arguments returns an empty list.
            null -> listOf()
            // '.map' accepts a lambda as an action.
            // 'it' refers to the collection item.
            else -> topicsProp.split(',').map { Topic(it.trim()) }
        }
        return User(username, topics)
    }
}
