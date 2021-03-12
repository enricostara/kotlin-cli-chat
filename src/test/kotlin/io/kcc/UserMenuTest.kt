package io.kcc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class UserMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("add"))
        assertNotEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("add", "enrico"))
        assertEquals(UserMenu::deleteUser, UserMenu.translateUserInput("delete"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput())
    }
}
