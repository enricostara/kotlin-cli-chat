package io.kcc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput("-h"))
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput("--help"))
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput())
        assertEquals(UserMenu::printHelpMessage, MainMenu.translateUserInput("user"))

        assertEquals(MainMenu::printVersion, MainMenu.translateUserInput("--version"))
    }
}
