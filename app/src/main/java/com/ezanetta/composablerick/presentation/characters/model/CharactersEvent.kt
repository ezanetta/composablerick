package com.ezanetta.composablerick.presentation.characters.model

sealed class CharactersEvent {
    object ShowLoading: CharactersEvent()
    object HideLoading: CharactersEvent()
}
