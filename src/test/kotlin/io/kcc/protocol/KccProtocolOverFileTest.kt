package io.kcc.protocol

import io.kcc.model.Host
import io.kcc.model.Topic
import io.kcc.model.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class KccProtocolOverFileTest {

    @AfterEach
    fun clean() {
        File("./").walk().filter { it.extension == "kcc" }.forEach { it.delete() }
    }

    @Test
    fun accept() {
        assert(KccProtocolOverFile.accept(Host("file:./")))
        assert(!KccProtocolOverFile.accept(Host("ftp:./")))
    }

    @Test
    fun acceptWithException() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.accept(Host("file:./%&#wrong"))
        }
    }

    @Test
    fun readTopics() {
        KccProtocolOverFile.accept(Host("file:./"))
        File(".kotlin#enrico.kcc").createNewFile()
        File(".java#enrico.kcc").createNewFile()
        val topics = KccProtocolOverFile.readTopics()
        assert(setOf(Topic("java"), Topic("kotlin")).containsAll(topics))
        assertEquals("#enrico", topics.first().owner?.name.toString())
    }

    @Test
    fun readTopic() {
        KccProtocolOverFile.accept(Host("file:./"))
        File(".kotlin#enrico.kcc").createNewFile()
        val topic = KccProtocolOverFile.readTopic("kotlin")
        assertEquals(User(User.Name("enrico")), topic.owner)
    }

    @Test
    fun createTopic() {
        KccProtocolOverFile.accept(Host("file:./"))
        KccProtocolOverFile.createTopic(Topic("kotlin-cli-chat", User(User.Name("enrico"))))
        assert(File(".kotlin-cli-chat#enrico.kcc").exists())
    }

    @Test
    fun createTopicWithExceptionByTopicAlreadyExists() {
        KccProtocolOverFile.accept(Host("file:./"))
        KccProtocolOverFile.createTopic(Topic("kotlin-cli-chat", User(User.Name("enrico"))))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.createTopic(Topic("kotlin-cli-chat", User(User.Name("other"))))
        }
    }

    @Test
    fun createTopicWithExceptionByBadChar() {
        KccProtocolOverFile.accept(Host("file:./"))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.createTopic(Topic("kotlin_cli_chat", User(User.Name("enrico"))))
        }
    }

    @Test
    fun createTopicWithExceptionByTooShort() {
        KccProtocolOverFile.accept(Host("file:./"))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.createTopic(Topic("ko", User(User.Name("enrico"))))
        }
    }

    @Test
    fun joinTopicWithExceptionByTopicDoesNotExist() {
        KccProtocolOverFile.accept(Host("file:./"))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.joinTopic("kotlin")
        }
    }

    @Test
    fun leaveTopicWithExceptionByTopicDoesNotExist() {
        KccProtocolOverFile.accept(Host("file:./"))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.leaveTopic("kotlin")
        }
    }

    @Test
    fun deleteTopic() {
        KccProtocolOverFile.accept(Host("file:./"))
        File(".kotlin#enrico.kcc").createNewFile()
        File(".java#enrico.kcc").createNewFile()
        File(".pascal#enrico.kcc").createNewFile()
        KccProtocolOverFile.deleteTopic(Topic("pascal", User(User.Name("enrico"))))
        val topics = KccProtocolOverFile.readTopics()
        assert(setOf(Topic("java"), Topic("kotlin")).containsAll(topics))
    }

    @Test
    fun deleteTopicWithExceptionByTopicDoesNotExist() {
        KccProtocolOverFile.accept(Host("file:./"))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.deleteTopic(Topic("kotlin", User(User.Name("enrico"))))
        }
    }

    @Test
    fun deleteTopicWithExceptionByNotOwner() {
        KccProtocolOverFile.accept(Host("file:./"))
        File(".kotlin#enrico.kcc").createNewFile()
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            KccProtocolOverFile.deleteTopic(Topic("kotlin", User(User.Name("other"))))
        }
    }

    @Test
    fun readMessages() {
    }

    @Test
    fun writeMessage() {
    }
}