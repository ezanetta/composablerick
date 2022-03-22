package com.ezanetta.composablerick.presentation.characterdetail.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ezanetta.composablerick.R
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.presentation.characterdetail.model.CharacterDetailState
import com.ezanetta.composablerick.presentation.randomcharacter.compose.AliveIndicator
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme

@Composable
fun CharacterSheetScreen(
    modifier: Modifier = Modifier,
    characterDetailState: CharacterDetailState
) {
    ComposableRickTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Character(
                character = characterDetailState.character
            )
        }
    }
}

@Composable
fun Character(
    modifier: Modifier = Modifier,
    character: Character
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop,
            model = character.image,
            contentDescription = character.name,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                fontSize = 24.sp,
                text = character.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                AliveIndicator(status = character.status)

                Text(
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = String.format(
                        stringResource(id = R.string.status_specie),
                        character.status,
                        character.species
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}