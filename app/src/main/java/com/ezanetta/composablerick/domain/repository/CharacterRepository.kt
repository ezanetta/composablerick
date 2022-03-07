package com.ezanetta.composablerick.domain.repository

import com.ezanetta.composablerick.domain.entity.Character

interface CharacterRepository {

    suspend fun getCharacterById(
        id: Int,
        onComplete: (Character) -> Unit,
        onError: (String?) -> Unit
    )
}