package io.kcc

data class User(
    val name: Name,
    val topics: List<Topic> = listOf()
) {

    data class Name(var value: String) {
        override fun toString() = "@$value"
    }

    override fun toString() = """
        |User: $name                
        |Subsc: ${
        when {
            topics.isEmpty() -> "no #topics"
            else -> topics.joinToString("\n|  - ", "\n|  - ", "") { it.name }
        }
    }""".trimMargin()
}

