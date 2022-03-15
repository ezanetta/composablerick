package com.ezanetta.composablerick.presentation.characters.model

import androidx.paging.PagingData
import com.ezanetta.composablerick.domain.entity.Character
import kotlinx.coroutines.flow.Flow

data class CharactersState(
    val isLoading: Boolean = false,
    val charactersPagingData: Flow<PagingData<Character>>? = null,
    val showError: Boolean = false
)
