package io.kcc.model

/**
 *  Use a standard class in order to validate constructor parameters
 *  [see](https://kotlinlang.org/docs/classes.html#constructors)
 */
class Topic(initName: String) {
    // Property 'value' initialized by the constructor 'initName' parameter after validation
    val name = validate(initName)

    private fun validate(value: String) = when {
        value.matches("^[a-zA-Z0-9-]{3,}$".toRegex()) -> value
        else -> throw IllegalArgumentException(
            "topic name '$value' is not valid! It can only contain letters, numbers, hyphen and be at least 3 characters long."
        )
    }

    override fun toString() = "/$name"

    // Since this is a standard class the equals()/hashCode() pair has been manually overridden
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        // 'is' is the same as 'instanceOf' in Java
        if (other == null || other !is Topic) return false
        return name == other.name
    }

    override fun hashCode() = name.hashCode()
}