package ru.wolfram.votes

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.wolfram.common.data.network.service.ApiServiceTestImpl1
import ru.wolfram.common.data.storage.LocalDataStorageTestImpl
import ru.wolfram.common.di.DaggerAppComponent
import ru.wolfram.common.presentation.test.NodeTags
import ru.wolfram.common.presentation.theme.AppTheme
import ru.wolfram.votes.di.DaggerVotesComponent
import ru.wolfram.votes.presentation.TestMainActivity
import ru.wolfram.votes.presentation.VotesScreen
import ru.wolfram.votes.presentation.VotesViewModel

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalTestApi::class)
class VotesTest {
    @get:Rule
    internal val composeTestRule = createAndroidComposeRule<TestMainActivity>()

    private fun throwRemoteException(): Nothing {
        throw RuntimeException("Remote exception")
    }

    @Test
    fun whenFailThenFailMessageAndTryButtonShown() {
        with(composeTestRule) {
            val appComponent = DaggerAppComponent
                .factory()
                .create(
                    composeTestRule.activity.applicationContext,
                    ApiServiceTestImpl1(
                        getVotesResult = { throwRemoteException() }
                    ),
                    LocalDataStorageTestImpl()
                )

            val votesComponent = DaggerVotesComponent
                .builder()
                .appComponent(appComponent)
                .build()

            val votesViewModelFactory = votesComponent.getVotesViewModelFactory()

            setContent {
                val viewModel = viewModel<VotesViewModel>(factory = votesViewModelFactory)

                AppTheme {
                    VotesScreen(
                        votesViewModel = viewModel,
                        onNavigateToVote = {}
                    ) {
                    }
                }
            }

            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.VOTES_FAILURE_MESSAGE
                ),
                1,
                3000
            )

            onNodeWithTag(NodeTags.VOTES_ITEMS_LAZY_COLUMN).assertDoesNotExist()
            onNodeWithTag(NodeTags.VOTES_FAILURE_MESSAGE).assertExists()
            onNodeWithTag(NodeTags.VOTES_TRY_LOAD_BUTTON).assertExists()
        }
    }

    @Test
    fun whenSuccessThenVotesLazyColumnShown() {
        with(composeTestRule) {
            val appComponent = DaggerAppComponent
                .factory()
                .create(
                    composeTestRule.activity.applicationContext,
                    ApiServiceTestImpl1(),
                    LocalDataStorageTestImpl()
                )

            val votesComponent = DaggerVotesComponent
                .builder()
                .appComponent(appComponent)
                .build()

            val votesViewModelFactory = votesComponent.getVotesViewModelFactory()

            setContent {
                val viewModel = viewModel<VotesViewModel>(factory = votesViewModelFactory)

                AppTheme {
                    VotesScreen(
                        votesViewModel = viewModel,
                        onNavigateToVote = {}
                    ) {
                    }
                }
            }

            waitUntilNodeCount(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.TestTag,
                    NodeTags.VOTES_ITEMS_LAZY_COLUMN
                ),
                1,
                3000
            )
            onNodeWithTag(NodeTags.VOTES_ITEMS_LAZY_COLUMN).assertExists()
            onNodeWithTag(NodeTags.VOTES_FAILURE_MESSAGE).assertDoesNotExist()
            onNodeWithTag(NodeTags.VOTES_TRY_LOAD_BUTTON).assertDoesNotExist()
        }
    }
}