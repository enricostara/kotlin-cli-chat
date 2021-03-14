package io.kcc.model

import java.net.URL

/**
 * See the comments in the User class
 */
data class Host(val url: URL) {
    override fun toString() = """
        host:
          url: $url
          schema: ${url.protocol}
          authority: ${url.authority ?: "localhost"} 
          path: ${url.path}
    """.trimIndent()
}

