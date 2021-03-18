package io.kcc

import io.kcc.model.Topic
import io.kcc.model.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ConfigurationTest {

    @BeforeAll
    fun init() {
        projectVersion = "0-test"
    }

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
        val config = Configuration("./").load()
        assert(config.configView.isEmpty())
    }

    @Test
    fun load() {
        val properties = mapOf(userName to "enrico").toProperties()
        properties.store(File(fileName).bufferedWriter(), "test")
        val config = Configuration("./").load().configView
        assertEquals(properties, config)
    }

    @Test
    fun readUser() {
        val configMap = mapOf(userName to "enrico", userTopics to "kotlin, java")
        val configuration = Configuration()
        val user = configuration.readUser(configMap)
        assertEquals("enrico", user.name.value)
        assertArrayEquals(arrayOf(Topic("kotlin"), Topic("java")), user.topics.toTypedArray())
    }

    @Test
    fun readUserWithException() {
        val configMap = mapOf(userTopics to "kotlin, java")
        val configuration = Configuration()
        assertThrows(IllegalStateException::class.java) { configuration.readUser(configMap) }
    }

    @Test
    fun createUser() {
        val config = Configuration().createUser("enrico")
        assertEquals("enrico", config.configView[userName])
    }

    @Test
    fun createUserWithException() {
        val configMap = hashMapOf(userName to "enrico", userTopics to "kotlin, java")
        val configuration = Configuration()
        assertThrows(IllegalStateException::class.java) {
            configuration.createUser("enrico", configMap)
        }
    }

    @Test
    fun createUserWithValidationExceptionByBadChar() {
        assertThrows(IllegalArgumentException::class.java) {
            Configuration().createUser("#enrico")
        }
    }

    @Test
    fun createUserWithValidationExceptionByTooShort() {
        assertThrows(IllegalArgumentException::class.java) {
            Configuration().createUser("en")
        }
    }

    @Test
    fun updateUser() {
        val configMap = hashMapOf(userName to "enrico", userTopics to "kotlin, java")
        Configuration().updateUser(User(User.Name("enrico_s"), arrayListOf(Topic("kotlin-cli-chat"))), configMap)
        assertEquals("enrico_s", configMap[userName])
        assertEquals("kotlin-cli-chat", configMap[userTopics])
    }

    @Test
    fun updateUserWithException() {
        val configuration = Configuration()
        assertThrows(IllegalStateException::class.java) {
            configuration.updateUser(
                User(
                    User.Name("enrico"),
                    arrayListOf(Topic("kotlin"), Topic("java"))
                )
            )
        }
    }

    @Test
    fun updateUserWithValidationException() {
        val configuration = Configuration()
        assertThrows(IllegalArgumentException::class.java) {
            configuration.updateUser(
                User(
                    User.Name("#enrico"),
                    arrayListOf(Topic("kotlin"), Topic("java"))
                )
            )
        }
    }

    @Test
    fun deleteUser() {
        val configMap = hashMapOf(userName to "enrico", userTopics to "kotlin, java")
        Configuration().deleteUser(configMap)
        assert(configMap.isEmpty())
    }

    @Test
    fun deleteUserWithException() {
        val configuration = Configuration()
        assertThrows(IllegalStateException::class.java) {
            configuration.deleteUser()
        }
    }

    @Test
    fun readHost() {
        val configMap = mapOf(hostUrl to "file:/users/enrico/kcc-server")
        val configuration = Configuration()
        val host = configuration.readHost(configMap)
        assertEquals("file", host.url.protocol)
        assert(host.url.authority.isNullOrEmpty())
        assertEquals("/users/enrico/kcc-server", host.url.path)
    }

    @Test
    fun registerHost() {
        val config = Configuration().registerHost("file:///users/enrico/kcc-server")
        assertEquals("file:/users/enrico/kcc-server", config.configView[hostUrl])
    }

    @Test
    fun registerHostWithoutScheme() {
        val config = Configuration().registerHost("/users/enrico/kcc-server")
        assertEquals("file:/users/enrico/kcc-server", config.configView[hostUrl])
    }

    @Test
    fun registerHostWithExceptionByBadScheme() {
        val configuration = Configuration()
        assertThrows(Exception::class.java) {
            configuration.registerHost("unknown:/path")
        }
    }

    @Test
    fun registerHostWithExceptionByUnsupportedScheme() {
        val configuration = Configuration()
        assertThrows(Exception::class.java) {
            configuration.registerHost("ftp:/path")
        }
    }

    @Test
    fun unregisterHost() {
        val configMap = hashMapOf(hostUrl to "file:/users/enrico/kcc-server")
        Configuration().unregisterHost(configMap)
        assert(configMap.isEmpty())
    }

    @Test
    fun store() {
        val configMap = mapOf(userName to "enrico", userTopics to "kotlin, java")
        Configuration("./").store(configMap)
        val properties = Properties()
        properties.load(File(fileName).inputStream())
        assertEquals(configMap, properties)
    }
}
