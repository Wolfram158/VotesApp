package ru.wolfram.votes_app

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.wolfram.common.data.network.dto.TitlesDto
import ru.wolfram.common.data.network.dto.VoteDto2
import ru.wolfram.common.data.network.service.ApiServiceTestImpl1
import ru.wolfram.common.data.storage.LocalDataStorageTestImpl
import ru.wolfram.common.di.DaggerAppComponent
import ru.wolfram.common.presentation.test.NodeTags
import ru.wolfram.common.presentation.theme.AppTheme
import ru.wolfram.votes_app.presentation.LocalAppComponent
import ru.wolfram.votes_app.presentation.NavGraph
import ru.wolfram.votes_app.presentation.TestMainActivity

@RunWith(AndroidJUnit4::class)
class VotesAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestMainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun registerThenEnterThenGetVotesThenGetVote() {
        with(composeTestRule) {
            val title = "Dagger 2 or Metro?"
            val variant1 = "Dagger 2"
            val variant2 = "Metro"
            val appComponent =
                DaggerAppComponent.factory().create(
                    composeTestRule.activity.applicationContext,
                    ApiServiceTestImpl1(
                        getVotesResult = {
                            TitlesDto(
                                listOf(title)
                            )
                        },
                        getVoteResult = listOf(
                            VoteDto2(
                                title,
                                variant1,
                                1
                            ),
                            VoteDto2(
                                title,
                                variant2,
                                2
                            )
                        )
                    ),
                    LocalDataStorageTestImpl()
                )

            setContent {
                AppTheme {
                    CompositionLocalProvider(LocalAppComponent provides appComponent) {
                        val navHostController = rememberNavController()
                        NavGraph(navHostController)
                    }
                }
            }

            onNodeWithTag(NodeTags.GATEWAY_SCREEN_REGISTER_BUTTON).performClick()
            waitForIdle()
            onNodeWithTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_USERNAME_TEXT_FIELD).performTextInput(
                "John"
            )
            onNodeWithTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_PASSWORD_TEXT_FIELD).performTextInput(
                "1234"
            )
            onNodeWithTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_EMAIL_TEXT_FIELD).performTextInput("john@gmail.com")
            onNodeWithTag(NodeTags.REGISTRATION_FOR_EMAIL_CODE_REGISTER_BUTTON).performClick()
            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.REGISTRATION_WITH_EMAIL_CODE_CONFIRM_BUTTON
                ),
                1,
                3000
            )
            onNodeWithTag(NodeTags.REGISTRATION_WITH_EMAIL_CODE_CODE_TEXT_FIELD).performTextInput("email code")
            onNodeWithTag(NodeTags.REGISTRATION_WITH_EMAIL_CODE_CONFIRM_BUTTON).performClick()
            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.GATEWAY_SCREEN_ENTER_BUTTON
                ),
                1,
                3000
            )
            onNodeWithTag(NodeTags.GATEWAY_SCREEN_ENTER_BUTTON).performClick()
            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.VOTES_ITEMS_LAZY_COLUMN
                ),
                1,
                3000
            )
            onNodeWithTag(title).performClick()
            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.VOTE_ITEMS_LAZY_COLUMN
                ),
                1,
                3000
            )
            onNodeWithText(variant1).assertExists()
            onNodeWithText(variant2).assertExists()
        }
    }
}