package io.kcc.model

import java.net.URL

/**
 *  Use a standard class in order to validate constructor parameters
 *  [see](https://kotlinlang.org/docs/classes.html#constructors)
 */
class Host(initUrl: String) {
    // Property 'value' initialized by the constructor 'initUrl' parameter after validation
    val url: URL = validate(initUrl)

    private fun validate(value: String) = when {
        value.matches("^[a-zA-Z0-9_]+:.*$".toRegex()) -> URL(value)
        else -> URL("file:$value")
    }

    override fun toString() = """
        host:
          url: $url
          schema: ${url.protocol}
          authority: ${url.authority ?: "localhost"} 
          path: ${url.path}
    """.trimIndent()


    // Since this is a standard class the equals()/hashCode() pair has been manually overridden
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        // 'is' is the same as 'instanceOf' in Java
        if (other == null || other !is Host) return false
        return url == other.url
    }

    override fun hashCode() = url.hashCode()
}

