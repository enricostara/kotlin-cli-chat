package io.kcc.model

/**
 * The main purpose of 'data class' is to hold data.
 * The compiler automatically derives equals(), hashCode(), toString() members from all properties
 * declared in the primary constructor.
 * [see](https://kotlinlang.org/docs/data-classes.html)
 */
data class User(
    val name: Name,
    val topics: List<Topic> = listOf()
) {

    /**
     * The 'trimMargin' trims leading whitespace characters followed by marginPrefix ('|' as default) from every line.
     * [see](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/trim-margin.html)
     */
    override fun toString() = """
        |user:
        |  name: $name                
        |  subsc: ${
        when {
            topics.isEmpty() -> "no /topics"
            else -> topics.joinToString("\n|    - ", "\n|    - ", "") { it.name }
        }
    }""".trimMargin()

    /**
     * Kotlin nested classes don’t have access to the outer class instance, unless you specifically request that.
     * They are like the static nested classes in Java.
     * [see](https://kotlinlang.org/docs/nested-classes.html#inner-classes)
     */
    class Name(initValue: String) {
        // Property 'value' initialized by the constructor 'initValue' parameter after validation
        var value = validate(initValue)
            // In Kotlin, getters and setters are optional and are auto-generated if you do not create them in your program.
            // In 'data class' it is not possible to create getters and setters and that is why User.Name is a standard class
            // The 'value' setter to validate any new value
            set(newValue) {
                field = validate(newValue)
            }

        private fun validate(value: String) = when {
            value.matches("^[a-zA-Z0-9_]{3,}$".toRegex()) -> value
            else -> throw IllegalArgumentException(
                "user name '$value' is not valid! It can only contain letters, numbers, underscores and be at least 3 characters long."
            )
        }

        override fun toString() = "#$value"

        // Since this is a standard class the equals()/hashCode() pair has been manually overridden
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            // 'is' is the same as 'instanceOf' in Java
            if (other == null || other !is Name) return false
            return value == other.value
        }

        override fun hashCode() = value.hashCode()
    }
}
