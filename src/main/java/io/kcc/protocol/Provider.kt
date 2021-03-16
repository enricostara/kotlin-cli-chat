package io.kcc.protocol

import io.kcc.model.Host

fun provideKccProtocolHandler(host: Host): KccProtocol = when {
    KccProtocolOverFile.accept(host) -> KccProtocolOverFile
    else -> throw IllegalArgumentException("unable to find a kcc-protocol handler that accepts the following scheme: ${host.url.protocol}")
}