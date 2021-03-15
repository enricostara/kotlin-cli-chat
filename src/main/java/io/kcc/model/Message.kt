package io.kcc.model

data class Message(val user: User, val topic: Topic, val content: String) {
    override fun toString(): String = "$topic $user > $content"
}
