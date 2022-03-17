package com.ezanetta.composablerick.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ezanetta.composablerick.data.datasource.AllCharactersDataSource
import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.repository.AllCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkAllCharactersRepository @Inject constructor(
    private val rickAndMortyApiClient: RickAndMortyApiClient
) : AllCharactersRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            pagingSourceFactory = { AllCharactersDataSource(rickAndMortyApiClient) },
            config = PagingConfig(pageSize = PAGE_SIZE)
        ).flow
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}