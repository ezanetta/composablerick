package com.ezanetta.composablerick.presentation.randomcharacter.model

import com.ezanetta.composablerick.domain.entity.Character

data class RandomCharacterState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val error: String? = null
)