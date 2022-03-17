package com.ezanetta.composablerick.data.networking

import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.*
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.getOrThrow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Response

class RickAndMortyApiServiceTest {

    @Test
    fun `getCharacterById SHOULD return a Character WHEN response is Success`() {
        runBlocking {
            // GIVEN
            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getCharacterById(CHARACTER_ID) }.thenReturn(
                    ApiResponse.of(SandwichInitializer.successCodeRange) {
                        Response.success(JesusChristCharacter)
                    }
                )
            }

            // WHEN
            val response = rickAndMortyApiClient.getCharacterById(CHARACTER_ID)
            val characterResponse = response.getOrNull()

            // THEN
            assertTrue(response is ApiResponse.Success<Character>)
            characterResponse?.let { character ->
                assertEquals(character, JesusChristCharacter)
            }
        }
    }

    @Test(expected = Exception::class)
    fun `getCharacterById SHOULD thrown an Exception WHEN response is Failure`() {
        runBlocking {
            // GIVEN
            val errorMessage = Exception("Error 500")
            val responseError = ApiResponse.error<Character>(errorMessage)

            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getCharacterById(CHARACTER_ID) }.thenReturn(
                    responseError
                )
            }

            // WHEN
            val response = rickAndMortyApiClient.getCharacterById(CHARACTER_ID)
            val characterResponse = response.getOrThrow()

            // THEN
            assertEquals(characterResponse, responseError)
        }
    }

    @Test
    fun `getAllCharacters SHOULD return an AllCharacters WHEN response is Success`() {
        runBlocking {
            // GIVEN
            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getAllCharacters(PAGE) }.thenReturn(
                    ApiResponse.of(SandwichInitializer.successCodeRange) {
                        Response.success(allCharacters)
                    }
                )
            }

            // WHEN
            val response = rickAndMortyApiClient.getAllCharacters(PAGE)
            val allCharacterResponse = response.getOrNull()

            // THEN
            assertTrue(response is ApiResponse.Success<AllCharacters>)
            allCharacterResponse?.let { allCharacters ->
                assertEquals(allCharacters, allCharacters)
            }
        }
    }

    @Test(expected = Exception::class)
    fun `getAllCharacters SHOULD thrown an Exception WHEN response is Failure`() {
        runBlocking {
            // GIVEN
            val errorMessage = Exception("Error 500")
            val responseError = ApiResponse.error<AllCharacters>(errorMessage)

            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getAllCharacters(PAGE) }.thenReturn(
                    responseError
                )
            }

            // WHEN
            val response = rickAndMortyApiClient.getAllCharacters(PAGE)
            val allCharactersResponse = response.getOrThrow()

            // THEN
            assertEquals(allCharactersResponse, responseError)
        }
    }

    private companion object {
        const val PAGE = 1
        const val CHARACTER_ID = 608
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

        val characterList = listOf(JesusChristCharacter, JesusChristCharacter)

        val info = Info(
            count = 20,
            pages = 42,
            next = "",
            prev = ""
        )

        val allCharacters = AllCharacters(
            info = info,
            results = characterList
        )
    }
}