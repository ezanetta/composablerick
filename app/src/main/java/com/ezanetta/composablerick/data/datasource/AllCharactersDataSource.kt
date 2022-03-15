package com.ezanetta.composablerick.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.Character
import com.skydoves.sandwich.getOrNull
import javax.inject.Inject

class AllCharactersDataSource @Inject constructor(
    private val rickAndMortyApiClient: RickAndMortyApiClient
) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ONE_POSITION)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ONE_POSITION)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: INITIAL_PAGE
        val response = rickAndMortyApiClient.getAllCharacters(page).getOrNull()

        return if (response != null) {
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == INITIAL_PAGE) null else page - ONE_PAGE,
                nextKey = if (response.results.isEmpty()) null else page + ONE_PAGE
            )
        } else {
            LoadResult.Error(Exception("Something went wrong"))
        }
    }

    private companion object {
        const val INITIAL_PAGE = 1
        const val ONE_PAGE = 1
        const val ONE_POSITION = 1
    }

}