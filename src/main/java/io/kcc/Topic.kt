package io.kcc

data class Topic(val name: String) {
    override fun toString() = "#$name"
}

