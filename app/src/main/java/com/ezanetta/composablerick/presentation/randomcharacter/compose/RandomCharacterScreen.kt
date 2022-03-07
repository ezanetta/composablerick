package com.ezanetta.composablerick.presentation.randomcharacter.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState

@Composable
fun RandomCharacterScreen(
    modifier: Modifier = Modifier,
    randomCharacterState: RandomCharacterState
) {
    randomCharacterState.character?.let {
        CharacterCard(character = it)
    }
}

@Composable
fun CharacterCard(
    character: Character
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name
        )
        Column {
            Text(character.name)
        }
    }
}