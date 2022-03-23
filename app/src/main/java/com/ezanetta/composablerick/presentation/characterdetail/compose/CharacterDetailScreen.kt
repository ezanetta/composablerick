package com.ezanetta.composablerick.presentation.characterdetail.compose

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ezanetta.composablerick.R
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.presentation.characterdetail.model.CharacterDetailState
import com.ezanetta.composablerick.presentation.randomcharacter.compose.AliveIndicator
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme

@Composable
fun CharacterSheetScreen(
    modifier: Modifier = Modifier,
    characterDetailState: CharacterDetailState,
    navController: NavController
) {
    ComposableRickTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Character(
                character = characterDetailState.character,
                navController = navController
            )
        }
    }
}

@Composable
fun Character(
    modifier: Modifier = Modifier,
    character: Character,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(contentAlignment = Alignment.TopStart) {
            val alphaValue = remember { 0.7f }

            AsyncImage(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop,
                model = character.image,
                contentDescription = character.name,
            )

            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .size(74.dp)
                    .padding(20.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.DarkGray.copy(alphaValue)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    backgroundColor = Color.DarkGray.copy(alphaValue)
                )
            ) {
                Icon(
                    Icons.Default.Close, contentDescription =
                    stringResource(id = R.string.character_close_screen_cd)
                )
            }
        }
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
                fontSize = 36.sp,
                text = character.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AliveIndicator(status = character.status)

                Text(
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(horizontal = 6.dp),
                    text = String.format(
                        stringResource(id = R.string.character_status_specie),
                        character.status,
                        character.species
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.character_last_known_location),
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp),
                text = character.location.name,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.character_gender),
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp),
                text = character.gender.name,
                overflow = TextOverflow.Ellipsis
            )

            AnimatedVisibility(
                visible = character.type.isNotEmpty()
            ) {
                Column {
                    Text(
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = stringResource(R.string.character_type),
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = character.type,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.character_first_seen_in),
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp),
                text = String.format(
                    stringResource(id = R.string.character_first_seen_episode),
                    Uri.parse(character.episode.first()).lastPathSegment
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}