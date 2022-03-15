package com.ezanetta.composablerick.presentation.characters.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.presentation.characters.model.CharactersState
import com.ezanetta.composablerick.presentation.randomcharacter.compose.CharacterCard
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    charactersState: CharactersState,
) {
    ComposableRickTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .wrapContentSize(Alignment.Center)
        ) {
            RenderCharacterList(charactersState)
        }
    }
}

@Composable
fun RenderCharacterList(
    charactersState: CharactersState,
) {
    charactersState.charactersPagingData?.let {
        CharacterList(charactersPagingData = it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    charactersPagingData: Flow<PagingData<Character>>
) {
    val characterListItems: LazyPagingItems<Character> =
        charactersPagingData.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(2),
        content = {
            items(characterListItems.itemCount) { index ->
                characterListItems[index]?.let {
                    CharacterCard(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 10.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        character = it,
                        imageHeight = 150.dp
                    )
                }
            }
        }
    )
}