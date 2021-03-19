package io.kcc.protocol

import io.kcc.model.Host

/**
 * Functions can be declared at the top level of a file, a class is not needed.
 * [see](https://kotlinlang.org/docs/functions.html#function-scope)
 */
fun provideKccProtocolHandler(host: Host): KccProtocol = when {
    KccProtocolOverFile.accept(host) -> KccProtocolOverFile
    else -> throw IllegalArgumentException("unable to find a kcc-protocol handler that accepts the following scheme: ${host.url.protocol}")
}