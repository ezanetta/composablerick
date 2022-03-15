package com.ezanetta.composablerick.data.network

import com.ezanetta.composablerick.domain.entity.AllCharacters
import com.ezanetta.composablerick.domain.entity.Character
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class RickAndMortyApiServiceImpl @Inject constructor(
    private val rickAndMortyApiClient: RickAndMortyApiClient
) : RickAndMortyApiService {

    override suspend fun getCharacterById(id: Int): ApiResponse<Character> {
        return rickAndMortyApiClient.getCharacterById(id)
    }

    override suspend fun getAllCharacters(page: Int): ApiResponse<AllCharacters> {
        return rickAndMortyApiClient.getAllCharacters(page)
    }
}