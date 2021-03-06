package com.ezanetta.composablerick.presentation.randomcharacter.compose


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ezanetta.composablerick.R
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.domain.entity.Status
import com.ezanetta.composablerick.presentation.navigation.navigateToCharacter
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterEvent
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.LOAD_CHARACTER_ACTION_BUTTON
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.LOAD_CHARACTER_BUTTON
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_CARD
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_IMAGE
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_LOADING
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_NAME
import com.ezanetta.composablerick.presentation.randomcharacter.model.Tags.RANDOM_CHARACTER_STATUS_AND_SPECIES
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme

@Composable
fun RandomCharacterScreen(
    modifier: Modifier = Modifier,
    randomCharacterState: RandomCharacterState,
    handleEvent: (event: RandomCharacterEvent) -> Unit,
    navController: NavController
) {
    ComposableRickTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            RenderCharacterCard(randomCharacterState, navController)
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
            CircularProgressIndicator(modifier = Modifier.testTag(RANDOM_CHARACTER_LOADING))
        }
    }
}

@Composable
private fun RenderCharacterCard(
    randomCharacterState: RandomCharacterState,
    navController: NavController
) {
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = !randomCharacterState.isLoading
    ) {
        randomCharacterState.character?.let {
            CharacterCard(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .clickable {
                        navigateToCharacter(it, navController)
                    }
                    .wrapContentWidth()
                    .wrapContentHeight(),
                character = it,
                imageHeight = 350.dp
            )
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
            modifier = Modifier
                .testTag(LOAD_CHARACTER_BUTTON)
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                modifier = Modifier.testTag(LOAD_CHARACTER_ACTION_BUTTON),
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
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    imageHeight: Dp,
) {
    Card(
        modifier = modifier
            .testTag(RANDOM_CHARACTER_CARD),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .testTag(RANDOM_CHARACTER_IMAGE)
                    .fillMaxWidth()
                    .height(imageHeight),
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
                    modifier = Modifier.testTag(RANDOM_CHARACTER_NAME),
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
                            .testTag(RANDOM_CHARACTER_STATUS_AND_SPECIES)
                            .padding(horizontal = 4.dp),
                        text = String.format(
                            stringResource(id = R.string.character_status_specie),
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
fun AliveIndicator(status: Status) {
    Canvas(
        modifier = Modifier.size(8.dp),
        onDraw = {
            drawCircle(color = status.color)
        })
}