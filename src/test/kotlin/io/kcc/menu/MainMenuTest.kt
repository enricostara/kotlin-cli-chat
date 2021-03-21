package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput())
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput(helpShortOption))
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput(helpOption))
        assertEquals(MainMenu::printVersion, MainMenu.translateUserInput(versionOption))
        assertEquals(UserMenu::readUser, MainMenu.translateUserInput(userMenuItem))
        assertEquals(HostMenu::readHost, MainMenu.translateUserInput(hostMenuItem))
        assertEquals(TopicMenu::readTopics, MainMenu.translateUserInput(topicMenuItem))

        val notMessageMenu = setOf<Function<Unit>>(
            MainMenu::printHelpMessage,
            MainMenu::printVersion,
            UserMenu::readUser,
            HostMenu::readHost,
            TopicMenu::readTopics
        )
        assertEquals(MainMenu::printHelpMessage, MainMenu.translateUserInput("kotlin"))
        assert(!notMessageMenu.contains(MainMenu.translateUserInput("/kotlin")))
        assert(
            !notMessageMenu.contains(
                MainMenu.translateUserInput(
                    "/kotlin",
                    "Hello"
                )
            )
        )
    }
}
