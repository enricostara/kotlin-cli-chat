package io.kcc

import java.net.URL

data class Host(val url: URL) {
    override fun toString() = ">> $url"
}

