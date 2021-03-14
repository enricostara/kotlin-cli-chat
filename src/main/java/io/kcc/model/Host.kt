package io.kcc.model

import java.net.URL

/**
 * See the comments in the User class
 */
class Host(initUrl: String) {

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
}

