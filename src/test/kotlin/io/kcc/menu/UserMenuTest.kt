package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(UserMenu::readUser, UserMenu.translateUserInput())
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput(helpShortOption))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput(helpOption))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput(createMenuItem))
        assertEquals(UserMenu::printHelpMessage, UserMenu.translateUserInput(renameMenuItem))
        assertEquals(UserMenu::deleteUser, UserMenu.translateUserInput(deleteMenuItem))
        assert(
            !setOf<Function<Unit>>(
                UserMenu::printHelpMessage,
                UserMenu::readUser,
                UserMenu::deleteUser,
                UserMenu::renameUser
            ).contains(
                UserMenu.translateUserInput(
                    createMenuItem,
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
                    renameMenuItem,
                    "enrico"
                )
            )
        )
    }
}
