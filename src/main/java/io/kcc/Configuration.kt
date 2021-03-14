package io.kcc

import io.kcc.model.Host
import io.kcc.model.Topic
import io.kcc.model.User
import java.io.File
import java.net.URL
import java.util.*

/**
 * 'const' is much like using the 'static' keyword in Java.
 *  Compiler inlines the 'const val' values where they are used.
 *  This inlining is much more efficient than getting a static value from a class.
 */
const val fileName = ".kcc"
const val errorMessage = "error:\n    "

const val userHome = "user.home"
const val userName = "user.name"
const val userTopics = "user.topics"
const val userNotFound = "no users have been created yet"
const val userAlreadyExists = "another user already exists"

const val hostUrl = "host.url"
const val hostNotRegistered = "No hosts have been registered yet!"

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
        val username = checkNotNull(config[userName]) { errorMessage + userNotFound }
        val topicsProp = config[userTopics]
        // 'when' is an expression and it is an advanced form of the Java 'switch-case' statement.
        // it can be used without argument, then the case expressions should evaluate as either true or false.
        val topics: List<Topic> = when {
            // 'isNullOrEmpty()' is an 'extension' method that can check if a string is null or empty
            // 'listOf()' without arguments returns an empty list.
            topicsProp.isNullOrEmpty() -> listOf()
            // '.map' accepts a lambda as an action.
            // 'it' refers to the collection item.
            else -> topicsProp.split(',').map { Topic(it.trim()) }
        }
        return User(User.Name(username), topics)
    }

    fun createUser(name: String, config: HashMap<String, String> = configMap): Configuration {
        check(config.isEmpty()) { userAlreadyExists }
        config[userName] = User.Name(name).value
        config[userTopics] = ""
        return this
    }

    fun updateUser(user: User, config: HashMap<String, String> = configMap): Configuration {
        checkNotNull(config[userName]) { errorMessage + userNotFound }
        config[userName] = user.name.value
        // 'joinToString' creates a string from all the elements separated using separator (',' as default)
        // and optionally accepts a lambda to map each element
        config[userTopics] = user.topics.joinToString { it.name }
        return this
    }

    fun deleteUser(config: HashMap<String, String> = configMap): Configuration {
        checkNotNull(config[userName]) { errorMessage + userNotFound }
        config.remove(userName)
        config.remove(userTopics)
        return this
    }

    fun store(config: Map<String, String> = configMap): Configuration {
        val configFile = File("$path${File.separator}$fileName")
        config.toProperties().store(configFile.outputStream(), "kotlin-cli-chat v$projectVersion")
        return this
    }


    fun readHost(config: Map<String, String> = configMap): Host {
        val hostUrl = checkNotNull(config[hostUrl]) { errorMessage + hostNotRegistered }
        return Host(URL(hostUrl))
    }

    fun registerHost(url: String, config: HashMap<String, String> = configMap): Configuration {

        config[hostUrl] = URL(url).toString()
        return this
    }

    fun unregisterHost(config: HashMap<String, String> = configMap): Configuration {
        checkNotNull(config[hostUrl]) { hostNotRegistered }
        config.remove(hostUrl)
        return this
    }
}
