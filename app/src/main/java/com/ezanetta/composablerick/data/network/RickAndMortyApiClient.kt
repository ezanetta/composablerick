package com.ezanetta.composablerick.data.network

import com.ezanetta.composablerick.domain.entity.Character
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApiClient {

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): ApiResponse<Character>
}