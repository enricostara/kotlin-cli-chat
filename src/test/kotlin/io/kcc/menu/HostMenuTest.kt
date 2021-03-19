package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HostMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(HostMenu::readHost, HostMenu.translateUserInput())
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput(helpShortOption))
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput(helpOption))
        assertEquals(HostMenu::printHelpMessage, HostMenu.translateUserInput(createMenuItem))
        assertEquals(HostMenu::unregisterHost, HostMenu.translateUserInput(deleteMenuItem))
        assert(
            !setOf<Function<Unit>>(
                HostMenu::printHelpMessage,
                HostMenu::readHost,
                HostMenu::unregisterHost
            ).contains(
                UserMenu.translateUserInput(
                    createMenuItem,
                    "file:/users/enrico"
                )
            )
        )
    }
}
