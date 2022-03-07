package com.ezanetta.composablerick.presentation.randomcharacter.model

sealed class RandomCharacterEvent {
    object GetNewCharacter: RandomCharacterEvent()
}