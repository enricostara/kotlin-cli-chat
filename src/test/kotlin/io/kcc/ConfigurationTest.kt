package io.kcc

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class ConfigurationTest {

    @AfterEach
    fun clean() {
        val config = File(fileName)
        if (config.exists()) {
            config.delete()
        }
    }

    @Test
    fun testPath() {
        // default path
        var configuration = Configuration()
        assertEquals(System.getProperty(userHome), configuration.path)
        // custom path
        configuration = Configuration("./")
        assertEquals("./", configuration.path)
    }

    @Test
    fun loadEmpty() {
        val config = Configuration().load()
        assert(config.isEmpty())
    }

    @Test
    fun load() {
        val properties = mapOf(userName to "enrico").toProperties()
        properties.store(File(fileName).bufferedWriter(), "test")
        val config = Configuration("./").load()
        assertEquals(properties, config)
    }

    @Test
    fun readUser() {
        val configMap = mapOf(userName to "enrico", userTopics to "kotlin, java")
        val configuration = Configuration()
        val user = configuration.readUser(configMap)
        assertEquals("enrico", user.name)
        assertArrayEquals(arrayOf(Topic("kotlin"), Topic("java")), user.topics.toTypedArray())
    }

    @Test
    fun readUserWithException() {
        val configMap = mapOf(userTopics to "kotlin, java")
        val configuration = Configuration()
        assertThrows(IllegalStateException::class.java) { configuration.readUser(configMap) }
    }
}
