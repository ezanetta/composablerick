package com.ezanetta.composablerick.presentation.characters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ezanetta.composablerick.data.datasource.AllCharactersDataSource
import com.ezanetta.composablerick.presentation.characters.model.CharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GetAllCharactersViewModel @Inject constructor(
    private val allCharactersDataSource: AllCharactersDataSource
) : ViewModel() {

    val uiState = MutableStateFlow(CharactersState())

    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        uiState.value = uiState.value.copy(
            isLoading = false,
            charactersPagingData = Pager(
                PagingConfig(PAGE_SIZE)
            ) { allCharactersDataSource }.flow.cachedIn(viewModelScope)
        )
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}