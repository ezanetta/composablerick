package com.ezanetta.composablerick.presentation.characters.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.extensions.toJsonStringFromCharacter
import com.ezanetta.composablerick.presentation.characters.model.CharactersEvent
import com.ezanetta.composablerick.presentation.characters.model.CharactersState
import com.ezanetta.composablerick.presentation.navigation.CharacterDetailDestination
import com.ezanetta.composablerick.presentation.randomcharacter.compose.CharacterCard
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    charactersState: CharactersState,
    handleEvent: (event: CharactersEvent) -> Unit,
    navController: NavController
) {
    ComposableRickTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .wrapContentSize(Alignment.Center)
        ) {
            RenderCharacterList(charactersState, handleEvent, navController)
            RenderLoading(charactersState = charactersState)
        }
    }
}

@Composable
fun RenderLoading(
    charactersState: CharactersState
) {
    if (charactersState.isLoading) {
        LoadingView()
    }
}

@Composable
fun RenderCharacterList(
    charactersState: CharactersState,
    handleEvent: (event: CharactersEvent) -> Unit,
    navController: NavController
) {
    charactersState.charactersPagingData?.let {
        CharacterList(
            charactersPagingData = it,
            handleEvent = handleEvent,
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    charactersPagingData: Flow<PagingData<Character>>,
    handleEvent: (event: CharactersEvent) -> Unit,
    navController: NavController
) {
    val characterListItems: LazyPagingItems<Character> =
        charactersPagingData.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        content = {

            items(characterListItems.itemCount) { index ->
                characterListItems[index]?.let {
                    CharacterCard(
                        modifier = Modifier
                            .clickable {
                                navigateToCharacter(it, navController)
                            }
                            .padding(vertical = 16.dp, horizontal = 10.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        character = it,
                        imageHeight = 150.dp
                    )
                }
            }

            characterListItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        handleEvent(CharactersEvent.ShowLoading)
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }

                    loadState.refresh is LoadState.NotLoading -> {
                        handleEvent(CharactersEvent.HideLoading)
                    }
                }
            }
        }
    )
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

private fun navigateToCharacter(
    character: Character,
    navController: NavController
) {
    val route = CharacterDetailDestination.getRouteWithArgument(toJsonStringFromCharacter(character))
    navController.navigate(route)
}