package io.kcc.protocol

import io.kcc.model.Host
import io.kcc.model.Topic
import org.junit.jupiter.api.AfterEach
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
    fun readTopics() {
        KccProtocolOverFile.accept(Host("file:./"))
        File(".kotlin.kcc").createNewFile()
        File(".java.kcc").createNewFile()
        val topics = KccProtocolOverFile.readTopics()
        assert(setOf(Topic("java"), Topic("kotlin")).containsAll(topics))
    }

    @Test
    fun createTopic() {
    }

    @Test
    fun joinTopic() {
    }

    @Test
    fun leaveTopic() {
    }

    @Test
    fun deleteTopic() {
    }

    @Test
    fun readMessages() {
    }

    @Test
    fun writeMessage() {
    }
}