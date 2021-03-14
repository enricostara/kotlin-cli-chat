package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(UserMenu::readUser, UserMenu.translateUserInput())
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("-h"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("--help"))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput("create"))
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
                    "create",
                    "enrico"
                )
            )
        )
        assert(
            !setOf<Function<Unit>>(
                UserMenu::printHelpMessage,
                UserMenu::readUser,
                UserMenu::deleteUser,
                UserMenu::createUser
            ).contains(
                UserMenu.translateUserInput(
                    "rename",
                    "enrico"
                )
            )
        )
    }
}
