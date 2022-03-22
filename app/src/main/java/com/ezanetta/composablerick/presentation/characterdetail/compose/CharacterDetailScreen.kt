package com.ezanetta.composablerick.presentation.characterdetail.compose

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
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