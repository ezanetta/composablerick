package com.ezanetta.composablerick.domain.usecase

import androidx.paging.PagingData
import com.ezanetta.composablerick.domain.entity.Character
import kotlinx.coroutines.flow.Flow

interface GetAllCharactersUseCase {
    fun fetchCharacters(): Flow<PagingData<Character>>
}