package com.ezanetta.composablerick.presentation.randomcharacter.compose


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ezanetta.composablerick.R
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.entity.Status
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterEvent
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme

@Composable
fun RandomCharacterScreen(
    modifier: Modifier = Modifier,
    randomCharacterState: RandomCharacterState,
    handleEvent: (event: RandomCharacterEvent) -> Unit
) {
    ComposableRickTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            RenderCharacterCard(randomCharacterState)
            RenderLoading(randomCharacterState)
            LoadCharacterButton(randomCharacterState, handleEvent)
        }
    }
}

@Composable
private fun RenderLoading(
    randomCharacterState: RandomCharacterState
) {
    if (randomCharacterState.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun RenderCharacterCard(
    randomCharacterState: RandomCharacterState
) {
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !randomCharacterState.isLoading
    ) {
        randomCharacterState.character?.let {
            CharacterCard(character = it)
        }
    }
}

@Composable
private fun LoadCharacterButton(
    randomCharacterState: RandomCharacterState,
    handleEvent: (event: RandomCharacterEvent) -> Unit
) {
    if (!randomCharacterState.isLoading) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                onClick = { handleEvent(RandomCharacterEvent.GetNewCharacter) }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(50.dp),
                    imageVector = Icons.Filled.Shuffle,
                    contentDescription = stringResource(R.string.reload_button_cd),
                    tint = Color.Gray
                )
            }
        }

    }
}

@Composable
private fun CharacterCard(
    character: Character
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        elevation = 4.dp
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(170.dp)
                    .height(170.dp),
                model = character.image,
                contentDescription = character.name,
            )
            Column(
                modifier = Modifier
                    .width(180.dp)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 4.dp
                    ),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    fontSize = 24.sp,
                    text = character.name,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AliveIndicator(status = character.status)

                    Text(
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 4.dp),
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
}

@Composable
private fun AliveIndicator(status: Status) {
    Canvas(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .size(8.dp),
        onDraw = {
            drawCircle(color = status.color)
        })
}