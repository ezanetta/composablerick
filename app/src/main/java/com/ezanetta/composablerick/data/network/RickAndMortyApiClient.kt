package com.ezanetta.composablerick.data.network

import com.ezanetta.composablerick.domain.entity.AllCharacters
import com.ezanetta.composablerick.domain.entity.Character
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiClient {

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): ApiResponse<Character>

    @GET("api/character/")
    suspend fun getAllCharacters(@Query("page") page: Int): ApiResponse<AllCharacters>
}