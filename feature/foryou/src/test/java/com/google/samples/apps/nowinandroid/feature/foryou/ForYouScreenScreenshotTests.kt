/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.feature.foryou

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.testing.util.captureMultiDevice
import com.google.samples.apps.nowinandroid.core.ui.NewsFeedUiState
import com.google.samples.apps.nowinandroid.core.ui.NewsFeedUiState.Success
import com.google.samples.apps.nowinandroid.core.ui.UserNewsResourcePreviewParameterProvider
import com.google.samples.apps.nowinandroid.feature.foryou.OnboardingUiState.Loading
import com.google.samples.apps.nowinandroid.feature.foryou.OnboardingUiState.NotShown
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import java.util.TimeZone

/**
 * Screenshot tests for the [ForYouScreen].
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, sdk = [33])
@LooperMode(LooperMode.Mode.PAUSED)
class ForYouScreenScreenshotTests {

    /**
     * Use a test activity to set the content on.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val userNewsResources = UserNewsResourcePreviewParameterProvider().values.first()

    @Before
    fun setTimeZone() {
        // Make time zone deterministic in tests
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun testForYouScreenPopulatedFeed() {
        composeTestRule.captureMultiDevice("ForYouScreenPopulatedFeed") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = NotShown,
                    feedState = Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                    deepLinkedUserNewsResource = null,
                    onDeepLinkOpened = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenLoading() {
        composeTestRule.captureMultiDevice("ForYouScreenLoading") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = Loading,
                    feedState = NewsFeedUiState.Loading,
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                    deepLinkedUserNewsResource = null,
                    onDeepLinkOpened = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenTopicSelection() {
        composeTestRule.captureMultiDevice("ForYouScreenTopicSelection") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = false,
                    onboardingUiState = OnboardingUiState.Shown(
                        topics = userNewsResources.flatMap { news -> news.followableTopics }
                            .distinctBy { it.topic.id },
                    ),
                    feedState = NewsFeedUiState.Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                    deepLinkedUserNewsResource = null,
                    onDeepLinkOpened = {},
                )
            }
        }
    }

    @Test
    fun testForYouScreenPopulatedAndLoading() {
        composeTestRule.captureMultiDevice("ForYouScreenPopulatedAndLoading") {
            NiaTheme {
                ForYouScreen(
                    isSyncing = true,
                    onboardingUiState = OnboardingUiState.Loading,
                    feedState = NewsFeedUiState.Success(
                        feed = userNewsResources,
                    ),
                    onTopicCheckedChanged = { _, _ -> },
                    saveFollowedTopics = {},
                    onNewsResourcesCheckedChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                    onTopicClick = {},
                    deepLinkedUserNewsResource = null,
                    onDeepLinkOpened = {},
                )
            }
        }
    }
}
