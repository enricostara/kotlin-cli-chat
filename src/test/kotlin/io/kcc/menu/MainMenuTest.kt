package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput())
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput("-h"))
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput("--help"))
        assertEquals(MainMenu::printVersion, MainMenu.translateUserInput("--version"))
        assertEquals(UserMenu::readUser, MainMenu.translateUserInput("user"))
        assertEquals(HostMenu::readHost, MainMenu.translateUserInput("host"))
    }
}
