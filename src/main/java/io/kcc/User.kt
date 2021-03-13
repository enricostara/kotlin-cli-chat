package io.kcc

/**
 * The main purpose of 'data class' is to hold data.
 * The compiler automatically derives equals(), hashCode(), toString() members from all properties
 * declared in the primary constructor
 */
data class User(
    val name: Name,
    val topics: List<Topic> = listOf()
) {

    /**
     *  'trimMargin' trims leading whitespace characters followed by marginPrefix ('|' as default) from every line
     */
    override fun toString() = """
        |User: $name                
        |Subsc: ${
        when {
            topics.isEmpty() -> "no #topics"
            else -> topics.joinToString("\n|  - ", "\n|  - ", "") { it.name }
        }
    }""".trimMargin()

    /**
     * Kotlin nested classes don’t have access to the outer class instance, unless you specifically request that.
     * They are like the static nested classes in Java.
     */
    class Name(initValue: String) {
        // Property 'value' initialized by the 'initValue' constructor parameter after validation
        var value = validate(initValue)
            // In Kotlin, getters and setters are optional and are auto-generated if you do not create them in your program.
            // In 'data' classes it is not possible to create getters and setters and that is why User.Name is a standard class
            // The property 'value' setter to validate any new value
            set(newValue) {
                field = validate(newValue)
            }

        private fun validate(value: String) = when {
            value.matches("^[a-zA-Z0-9_]*$".toRegex()) -> value
            else -> throw IllegalArgumentException("User name '$value' is not valid! It can contain only letters, numbers, and '_'")
        }

        override fun toString() = "@$value"

        // Since User.Name is a standard class the equals()/hashCode() pair has been manually overridden
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            // 'is' is the same as 'instanceOf' in Java
            if (other == null || other !is Name) return false
            return value == other.value
        }

        override fun hashCode() = value.hashCode()
    }
}

