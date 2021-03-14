package io.kcc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HostMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(HostMenu::readHost, HostMenu.translateUserInput())
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput("-h"))
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput("--help"))
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput("register"))
        assertEquals(HostMenu::unregisterHost, HostMenu.translateUserInput("unregister"))
        assert(
            !setOf<Function<Unit>>(
                HostMenu::printHelpMessage,
                HostMenu::readHost,
                HostMenu::unregisterHost
            ).contains(
                UserMenu.translateUserInput(
                    "register",
                    "file:///Users/enrico"
                )
            )
        )
    }
}
