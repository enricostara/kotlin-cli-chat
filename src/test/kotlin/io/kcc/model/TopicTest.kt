package io.kcc.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TopicTest {

    @Test
    fun createTopicWithExceptionByWrongName1() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Topic("Enrico")
        }
    }

    @Test
    fun createTopicWithExceptionByWrongName2() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Topic("1enrico")
        }
    }

    @Test
    fun createTopicWithExceptionByWrongName3() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Topic("en")
        }
    }

    @Test
    fun createTopicWithExceptionByWrongName4() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Topic("enrico_")
        }
    }

    @Test
    fun createTopicWithExceptionByWrongName5() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Topic("e123456789012345678901234")
        }
    }
}
