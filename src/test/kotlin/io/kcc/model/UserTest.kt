package io.kcc.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UserTest {

    @Test
    fun joinTopic() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("java")))
        user.joinTopic(Topic("kotlin"))
        Assertions.assertArrayEquals(arrayOf(Topic("java"), Topic("kotlin")), user.topics.toTypedArray())
    }

    @Test
    fun joinTopicWithExceptionByAlreadyJoined() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("java")))
        Assertions.assertThrows(Exception::class.java) {
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
        Assertions.assertThrows(Exception::class.java) {
            user.leaveTopic(Topic("kotlin"))
        }
    }

    @Test
    fun validateTopics() {
        val user = User(User.Name("enrico"), arrayListOf(Topic("pascal"), Topic("kotlin"), Topic("java")))
        user.validateTopics(listOf(Topic("kotlin"), Topic("java")))
        Assertions.assertArrayEquals(arrayOf(Topic("kotlin"), Topic("java")), user.topics.toTypedArray())
    }
}
