package io.kcc.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UserTest {

    @Test
    fun createUserWithExceptionByWrongName1() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            User(User.Name("Enrico"))
        }
    }

    @Test
    fun createUserWithExceptionByWrongName2() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            User(User.Name("1enrico"))
        }
    }

    @Test
    fun createUserWithExceptionByWrongName3() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            User(User.Name("en"))
        }
    }

    @Test
    fun createUserWithExceptionByWrongName4() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            User(User.Name("enrico-"))
        }
    }

    @Test
    fun createUserWithExceptionByWrongName5() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            User(User.Name("e123456789012"))
        }
    }

    @Test
    fun joinTopic() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("java")))
        user.joinTopic(Topic("kotlin"))
        Assertions.assertArrayEquals(arrayOf(Topic("java"), Topic("kotlin")), user.topics.toTypedArray())
    }

    @Test
    fun joinTopicWithExceptionByAlreadyJoined() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("java")))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            user.joinTopic(Topic("java"))
        }
    }

    @Test
    fun leaveTopic() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("pascal"), Topic("kotlin"), Topic("java")))
        user.leaveTopic(Topic("pascal"))
        Assertions.assertArrayEquals(arrayOf(Topic("kotlin"), Topic("java")), user.topics.toTypedArray())
    }

    @Test
    fun leaveTopicWithExceptionByNotYetBeenJoined() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("java")))
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            user.leaveTopic(Topic("kotlin"))
        }
    }

    @Test
    fun validateTopics() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("pascal"), Topic("kotlin"), Topic("java")))
        user.validateTopics(setOf(Topic("kotlin"), Topic("java")))
        Assertions.assertArrayEquals(arrayOf(Topic("kotlin"), Topic("java")), user.topics.toTypedArray())
    }
}
