package com.ezanetta.composablerick.data.repository

import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject

class NetworkCharacterRepository @Inject constructor(
    private val rickAndMortyApiClient: RickAndMortyApiClient
) : CharacterRepository {

    override suspend fun getCharacterById(
        id: Int,
        onComplete: (Character) -> Unit,
        onError: () -> Unit
    ) {
        val response = rickAndMortyApiClient.getCharacterById(id)
        response.onSuccess {
            onComplete(data)
        }.onError {
            onError()
        }.onException {
            onError()
        }
    }
}