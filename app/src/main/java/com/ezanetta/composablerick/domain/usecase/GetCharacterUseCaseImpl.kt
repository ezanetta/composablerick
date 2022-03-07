package com.ezanetta.composablerick.domain.usecase

import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterUseCase {

    override suspend fun execute(
        id: Int,
        onComplete: (Character) -> Unit,
        onError: (String?) -> Unit
    ) {
        characterRepository.getCharacterById(
            id = id,
            onComplete = onComplete,
            onError = onError
        )
    }
}