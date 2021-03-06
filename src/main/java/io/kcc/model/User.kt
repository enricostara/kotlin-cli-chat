package io.kcc.model

/**
 * The main purpose of 'data class' is to hold data.
 * The compiler automatically derives equals(), hashCode(), toString() members from all properties
 * declared in the primary constructor.
 * [see](https://kotlinlang.org/docs/data-classes.html)
 */
data class User(
    var name: Name,
    private val currentTopics: MutableList<Topic> = mutableListOf()
) {
    val topics: List<Topic>
        get() = currentTopics.toList()

    fun joinTopic(topic: Topic) {
        require(!currentTopics.contains(topic)) { "topic $topic already joined!" }
        currentTopics.add(topic)
    }

    fun joinedTopic(topic: Topic): Boolean = currentTopics.contains(topic)

    fun leaveTopic(topic: Topic) {
        require(currentTopics.contains(topic)) { "cannot leave the topic $topic, it has not been joined!" }
        currentTopics.remove(topic)
    }

    fun validateTopics(hostTopics: Set<Topic>) {
        // Retains only the topics that are contained in the host topic collection.
        currentTopics.retainAll(hostTopics)
    }

    /**
     * The 'trimMargin' trims leading whitespace characters followed by marginPrefix ('|' as default) from every line.
     * [see](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/trim-margin.html)
     */
    override fun toString() = """
        |user:
        |  name: $name                
        |  topics: ${
        when {
            currentTopics.isEmpty() -> "no /topics"
            else -> currentTopics.joinToString("\n|    - ", "\n|    - ", "") { it.toString() }
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
            value.matches("^[a-z][a-z0-9_]{2,11}$".toRegex()) -> value
            else -> throw IllegalArgumentException(
                "user name '$value' is not valid! It can only contain lowercase letters, numbers, underscores, start with a letter and be 3-12 characters in length"
            )
        }

        override fun toString() = "'$value'"

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
