package io.kcc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(UserMenu::readUser, UserMenu.translateUserInput())
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("-h"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("--help"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("add"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("rename"))
        assertEquals(UserMenu::deleteUser, UserMenu.translateUserInput("delete"))
        assert(
            !setOf<Function<Unit>>(
                UserMenu::printHelpMessage,
                UserMenu::readUser,
                UserMenu::deleteUser,
                UserMenu::renameUser
            ).contains(
                UserMenu.translateUserInput(
                    "add",
                    "enrico"
                )
            )
        )
        assert(
            !setOf<Function<Unit>>(
                UserMenu::printHelpMessage,
                UserMenu::readUser,
                UserMenu::deleteUser,
                UserMenu::addUser
            ).contains(
                UserMenu.translateUserInput(
                    "rename",
                    "enrico"
                )
            )
        )
    }
}
