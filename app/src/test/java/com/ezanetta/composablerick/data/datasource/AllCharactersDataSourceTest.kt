package com.ezanetta.composablerick.data.datasource

import androidx.paging.PagingSource
import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.*
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class AllCharactersDataSourceTest {

    private val rickAndMortyApiClient: RickAndMortyApiClient = mock()
    private val allCharactersDataSource = AllCharactersDataSource(rickAndMortyApiClient)

    @Test
    fun `initial load SHOULD return a list of valid characters WHEN api client response success`() {
        runBlocking {
            // GIVEN
            whenever(rickAndMortyApiClient.getAllCharacters(FIRST_PAGE))
                .thenReturn(
                    ApiResponse.of(SandwichInitializer.successCodeRange) {
                        Response.success(allCharacters)
                    }
                )

            val params: PagingSource.LoadParams<Int> = PagingSource.LoadParams.Refresh(
                FIRST_PAGE,
                LOAD_SIZE,
                false
            )
            val expectedResult = PagingSource.LoadResult.Page(
                characterList,
                null,
                NEXT_PAGE
            )

            // WHEN
            val result = allCharactersDataSource.load(params)

            // THEN
            assertEquals(expectedResult, result)
        }
    }

    @Test(expected = Exception::class)
    fun `initial load SHOULD return an error WHEN api client response fails`() {
        runBlocking {
            // GIVEN
            val errorMessage = Exception("Something went wrong")
            val responseError = ApiResponse.error<AllCharacters>(errorMessage)

            whenever(rickAndMortyApiClient.getAllCharacters(FIRST_PAGE))
                .thenReturn(
                    responseError
                )

            val params: PagingSource.LoadParams<Int> = PagingSource.LoadParams.Refresh(
                FIRST_PAGE,
                LOAD_SIZE,
                false
            )
            val expectedResult = PagingSource.LoadResult.Error<Int, Character>(errorMessage)

            // WHEN
            val result = allCharactersDataSource.load(params)

            // THEN
            assertEquals(expectedResult, result)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
        const val NEXT_PAGE = 2
        const val LOAD_SIZE = 20

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