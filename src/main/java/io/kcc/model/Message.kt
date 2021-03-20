package io.kcc.model

/**
 * The main purpose of 'data class' is to hold data.
 * The compiler automatically derives equals(), hashCode(), toString() members from all properties
 * declared in the primary constructor.
 * [see](https://kotlinlang.org/docs/data-classes.html)
 */
data class Message(val topic: Topic, val userName: String, val content: String) {
    override fun toString(): String = "$topic | $userName > $content"
}
