package com.ezanetta.composablerick.domain.usecase

import com.ezanetta.composablerick.domain.entity.Character

interface GetCharacterUseCase {
    suspend fun execute(
        id: Int,
        onComplete: (Character) -> Unit,
        onError: (String?) -> Unit
    )
}