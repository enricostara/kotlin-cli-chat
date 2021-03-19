package io.kcc.menu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TopicMenuTest {

    @Test
    fun translateUserInput() {
        assertEquals(TopicMenu::readTopics, TopicMenu.translateUserInput())
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(helpShortOption))
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(helpOption))
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(createMenuItem))
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(joinMenuItem))
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(leaveMenuItem))
        assertEquals(TopicMenu::printHelpMessage, TopicMenu.translateUserInput(deleteMenuItem))
        assert(
            !setOf<Function<Unit>>(
                TopicMenu::printHelpMessage,
                TopicMenu::readTopics,
                TopicMenu::joinTopic,
                TopicMenu::leaveTopic,
                TopicMenu::deleteTopic
            ).contains(
                TopicMenu.translateUserInput(
                    createMenuItem,
                    "kotlin"
                )
            )
        )
        assert(
            !setOf<Function<Unit>>(
                TopicMenu::printHelpMessage,
                TopicMenu::readTopics,
                TopicMenu::createTopic,
                TopicMenu::leaveTopic,
                TopicMenu::deleteTopic
            ).contains(
                TopicMenu.translateUserInput(
                    joinMenuItem,
                    "java"
                )
            )
        )
        assert(
            !setOf<Function<Unit>>(
                TopicMenu::printHelpMessage,
                TopicMenu::readTopics,
                TopicMenu::createTopic,
                TopicMenu::joinTopic,
                TopicMenu::deleteTopic
            ).contains(
                TopicMenu.translateUserInput(
                    leaveMenuItem,
                    "pascal"
                )
            )
        )
        assert(
            !setOf<Function<Unit>>(
                TopicMenu::printHelpMessage,
                TopicMenu::readTopics,
                TopicMenu::createTopic,
                TopicMenu::joinTopic,
                TopicMenu::leaveTopic,
            ).contains(
                TopicMenu.translateUserInput(
                    deleteMenuItem,
                    "pascal"
                )
            )
        )
    }
}
