package io.kcc.menu

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class MessageMenuTest {

    @Test
    fun evaluateQueryTopicOnly() {
        val (topicName, takeLast, userName) = MessageMenu.evaluateQuery("kotlin", 10)
        assertEquals("kotlin", topicName)
        assertEquals(10, takeLast)
        assertNull(userName)
    }

    @Test
    fun evaluateQueryTopicWithTakeLast() {
        val (topicName, takeLast, userName) = MessageMenu.evaluateQuery("kotlin/5", 10)
        assertEquals("kotlin", topicName)
        assertEquals(5, takeLast)
        assertNull(userName)
    }

    @Test
    fun evaluateQueryTopicWithUser() {
        val (topicName, takeLast, userName) = MessageMenu.evaluateQuery("kotlin/enrico", 10)
        assertEquals("kotlin", topicName)
        assertEquals(10, takeLast)
        assertEquals("enrico", userName)
    }

    @Test
    fun evaluateQueryTopicWithUserAndTakeLast() {
        val (topicName, takeLast, userName) = MessageMenu.evaluateQuery("kotlin/enrico/5", 10)
        assertEquals("kotlin", topicName)
        assertEquals(5, takeLast)
        assertEquals("enrico", userName)
    }

    @Test
    fun evaluateQueryWithExceptionByTopicWithUserAndNotNumber() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            MessageMenu.evaluateQuery("kotlin/enrico/not-number", 10)
        }
    }
}