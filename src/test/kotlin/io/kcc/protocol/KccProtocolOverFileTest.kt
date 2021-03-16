package io.kcc.protocol

import io.kcc.model.Host
import org.junit.jupiter.api.Test

internal class KccProtocolOverFileTest {

    @Test
    fun accept() {
        assert(KccProtocolOverFile.accept(Host("file:./")))
        assert(!KccProtocolOverFile.accept(Host("ftp:./")))
    }

    @Test
    fun readTopics() {
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