package com.ezanetta.composablerick.presentation.randomcharacter.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ezanetta.composablerick.R
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.entity.Gender
import com.ezanetta.composablerick.domain.entity.Location
import com.ezanetta.composablerick.domain.entity.Status
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterEvent
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.LOAD_CHARACTER_ACTION_BUTTON
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.LOAD_CHARACTER_BUTTON
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_CARD
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_IMAGE
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_LOADING
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_NAME
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_STATUS_AND_SPECIES
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class RandomCharacterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val targetContext = InstrumentationRegistry
        .getInstrumentation().targetContext

    private val navController: NavController = mock()

    @Test
    fun progressBarDisplayedWhileIsLoading() {
        // GIVEN

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(isLoading = true),
                handleEvent = {},
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun progressBarNotDisplayedWhileIsNotLoading() {
        // GIVEN

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(isLoading = false),
                handleEvent = {},
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun randomCharacterCardNotDisplayedWhileLoading() {
        // GIVEN

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(isLoading = true),
                handleEvent = {},
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_CARD)
            .assertDoesNotExist()
    }

    @Test
    fun loadCharacterButtonNotDisplayedWhileLoading() {
        // GIVEN

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(isLoading = true),
                handleEvent = {},
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(LOAD_CHARACTER_BUTTON)
            .assertDoesNotExist()
    }

    @Test
    fun randomCharacterCardShouldRenderCharacterValues() {
        // GIVEN

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(
                    isLoading = false,
                    character = JesusChristCharacter
                ),
                handleEvent = {},
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_NAME, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals(
                JesusChristCharacter.name
            )

        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_IMAGE, useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(RANDOM_CHARACTER_STATUS_AND_SPECIES, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals(
                String.format(
                    targetContext.getString(R.string.character_status_specie,
                        JesusChristCharacter.status,
                        JesusChristCharacter.species)
                )
            )
    }

    @Test
    fun loadCharacterTriggered() {
        // GIVEN
        val onLoadCharacter: (RandomCharacterEvent) -> Unit = mock()

        // WHEN
        composeTestRule.setContent {
            RandomCharacterScreen(
                randomCharacterState = RandomCharacterState(
                    isLoading = false,
                    character = JesusChristCharacter
                ),
                handleEvent = onLoadCharacter,
                navController = navController
            )
        }

        // THEN
        composeTestRule
            .onNodeWithTag(LOAD_CHARACTER_ACTION_BUTTON)
            .performClick()

        verify(onLoadCharacter).invoke(RandomCharacterEvent.GetNewCharacter)
    }

    private companion object {
        val JesusChristCharacter = Character(
            id = "608",
            name = "Jesus Christ",
            status = Status.Alive,
            species = "Human",
            type = "Soulless Puppet",
            gender = Gender.Male,
            location = Location("Ricks’s Story"),
            image = "https://rickandmortyapi.com/api/character/avatar/608.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/37")
        )
    }
}