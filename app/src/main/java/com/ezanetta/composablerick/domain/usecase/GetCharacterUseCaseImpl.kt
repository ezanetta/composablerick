package com.ezanetta.composablerick.domain.usecase

import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterUseCase {

    override suspend fun execute(
        onComplete: (Character) -> Unit,
        onError: (String?) -> Unit
    ) {
        characterRepository.getCharacterById(
            id = getRandomId(),
            onComplete = onComplete,
            onError = onError
        )
    }

    private fun getRandomId() = (FIRST_CHARACTER_INDEX..LAST_CHARACTER_INDEX).random()

    private companion object {
        const val FIRST_CHARACTER_INDEX = 1
        const val LAST_CHARACTER_INDEX = 671
    }
}