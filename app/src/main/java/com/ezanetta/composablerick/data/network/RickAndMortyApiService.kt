package com.ezanetta.composablerick.data.network

import com.ezanetta.composablerick.domain.entity.AllCharacters
import com.ezanetta.composablerick.domain.entity.Character
import com.skydoves.sandwich.ApiResponse

interface RickAndMortyApiService {
    suspend fun getCharacterById(id: Int): ApiResponse<Character>
    suspend fun getAllCharacters(page: Int): ApiResponse<AllCharacters>
}