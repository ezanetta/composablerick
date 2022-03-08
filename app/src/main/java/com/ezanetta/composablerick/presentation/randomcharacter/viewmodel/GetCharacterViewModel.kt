package com.ezanetta.composablerick.presentation.randomcharacter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezanetta.composablerick.domain.usecase.GetCharacterUseCase
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterEvent
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharacterViewModel @Inject constructor(
    private val characterUseCase: GetCharacterUseCase
) : ViewModel() {
    val uiState = MutableStateFlow(RandomCharacterState())

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {
        setIsLoading()
        viewModelScope.launch {
            characterUseCase.execute(
                {
                    uiState.value = uiState.value.copy(
                        character = it,
                        isLoading = false
                    )
                }, {
                    uiState.value = uiState.value.copy(
                        error = it,
                        isLoading = true
                    )
                })
        }
    }

    private fun setIsLoading() {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
    }

    fun handleEvent(randomCharacterEvent: RandomCharacterEvent) {
        if (randomCharacterEvent == RandomCharacterEvent.GetNewCharacter) {
            fetchCharacter()
        }
    }
}