package com.ezanetta.composablerick.domain.repository

import androidx.paging.PagingData
import com.ezanetta.composablerick.domain.entity.Character
import kotlinx.coroutines.flow.Flow

interface AllCharactersRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}