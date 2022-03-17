package com.ezanetta.composablerick.domain.usecase

import androidx.paging.PagingData
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.repository.AllCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCaseImpl @Inject constructor(
    private val allCharactersRepository: AllCharactersRepository
) : GetAllCharactersUseCase {

    override fun fetchCharacters(): Flow<PagingData<Character>> {
        return allCharactersRepository.getCharacters()
    }
}