package io.kcc

import java.io.File
import java.util.*

const val fileName = ".kcc"
const val userHome = "user.home"
const val userName = "user.name"
const val userTopics = "user.topics"

class Configuration(val path: String = System.getProperty(userHome)) {

    private val configMap = hashMapOf<String, String>()

    fun load(): Map<String, String> {
        val configFile = File("$path${File.separator}$fileName")
        if (configFile.exists()) {
            val config = Properties()
            config.load(configFile.inputStream())
            config.forEach { k, v -> configMap[k.toString()] = v.toString() }
        }
        return configMap
    }

    fun readUser(config: Map<String, String> = configMap): User {
        val username = checkNotNull(config[userName]) { "username not found" }
        val topics = when (val topicsProp = config[userTopics]) {
            null -> listOf()
            else -> topicsProp.split(',').map { Topic(it.trim()) }
        }
        return User(username, topics)
    }
}
