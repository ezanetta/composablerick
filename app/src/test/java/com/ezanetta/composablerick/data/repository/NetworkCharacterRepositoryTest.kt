package com.ezanetta.composablerick.data.repository

import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.entity.Gender
import com.ezanetta.composablerick.domain.entity.Location
import com.ezanetta.composablerick.domain.entity.Status
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class NetworkCharacterRepositoryTest {

    @Test
    fun `onComplete should be invoked when getCharacterById response is Successful`() {
        runBlocking {
            // GIVEN
            val characterId = 608
            val onComplete: (Character) -> Unit = mock()
            val onError: (String?) -> Unit = mock()

            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getCharacterById(characterId) }.thenReturn(
                    ApiResponse.of(SandwichInitializer.successCodeRange) {
                        Response.success(JesusChristCharacter)
                    }
                )
            }
            val networkCharacterRepository = NetworkCharacterRepository(rickAndMortyApiClient)

            // WHEN
            networkCharacterRepository.getCharacterById(
                characterId,
                onComplete,
                onError
            )

            // THEN
            verify(onComplete).invoke(JesusChristCharacter)
        }
    }

    @Test
    fun `onError should be invoked when getCharacterById response is Failure`() {
        runBlocking {
            // GIVEN
            val characterId = 608
            val onComplete: (Character) -> Unit = mock()
            val onError: (String?) -> Unit = mock()
            val errorMessage = Exception("Error 500")
            val responseError = ApiResponse.error<Character>(errorMessage)

            val rickAndMortyApiClient: RickAndMortyApiClient = mock {
                onBlocking { getCharacterById(characterId) }.thenReturn(
                    responseError
                )
            }
            val networkCharacterRepository = NetworkCharacterRepository(rickAndMortyApiClient)

            // WHEN
            networkCharacterRepository.getCharacterById(
                characterId,
                onComplete,
                onError
            )

            // THEN
            verify(onError, times(1))
        }
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