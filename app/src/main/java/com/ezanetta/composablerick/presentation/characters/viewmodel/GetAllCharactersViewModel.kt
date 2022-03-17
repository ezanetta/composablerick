package com.ezanetta.composablerick.presentation.characters.viewmodel

import androidx.lifecycle.ViewModel
import com.ezanetta.composablerick.domain.usecase.GetAllCharactersUseCase
import com.ezanetta.composablerick.presentation.characters.model.CharactersEvent
import com.ezanetta.composablerick.presentation.characters.model.CharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class GetAllCharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(CharactersState())

    init {
        fetchAllCharacters()
    }

    private fun fetchAllCharacters() {
        uiState.value = uiState.value.copy(
            charactersPagingData = getAllCharactersUseCase.fetchCharacters()
        )
    }

    fun handleEvent(charactersEvent: CharactersEvent) {
        when (charactersEvent) {

            CharactersEvent.HideLoading -> {
                uiState.value = uiState.value.copy(
                    isLoading = false
                )
            }

            CharactersEvent.ShowLoading -> {
                uiState.value = uiState.value.copy(
                    isLoading = true
                )
            }
        }
    }
}