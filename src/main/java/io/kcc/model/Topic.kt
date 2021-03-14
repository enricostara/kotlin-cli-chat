package io.kcc.model

/**
 * See the comments in the User class
 */
data class Topic(val name: String) {
    override fun toString() = "/$name"
}

