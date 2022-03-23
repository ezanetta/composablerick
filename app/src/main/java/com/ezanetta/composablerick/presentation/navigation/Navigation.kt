package com.ezanetta.composablerick.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.extensions.fromJsonStringToCharacter
import com.ezanetta.composablerick.extensions.toJsonStringFromCharacter
import com.ezanetta.composablerick.presentation.characterdetail.compose.CharacterSheetScreen
import com.ezanetta.composablerick.presentation.characterdetail.model.CharacterDetailState
import com.ezanetta.composablerick.presentation.characters.compose.CharactersScreen
import com.ezanetta.composablerick.presentation.characters.viewmodel.GetAllCharactersViewModel
import com.ezanetta.composablerick.presentation.randomcharacter.compose.RandomCharacterScreen
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    getCharacterViewModel: GetCharacterViewModel,
    getAllCharactersViewModel: GetAllCharactersViewModel
) {
    val transitionAnimationDuration = 300

    AnimatedNavHost(
        navController,
        startDestination = BottomNavItem.Random.screenRoute,
        Modifier.padding(innerPadding)
    ) {
        composable(BottomNavItem.Random.screenRoute) {
            RandomCharacterScreen(
                randomCharacterState = getCharacterViewModel
                    .uiState
                    .collectAsState()
                    .value,
                handleEvent = getCharacterViewModel::handleEvent,
                navController = navController
            )
        }

        composable(BottomNavItem.Characters.screenRoute) {
            CharactersScreen(
                charactersState = getAllCharactersViewModel
                    .uiState
                    .collectAsState()
                    .value,
                handleEvent = getAllCharactersViewModel::handleEvent,
                navController = navController
            )
        }

        composable(
            CharacterDetailDestination.ROUTE_WITH_ARG,
            enterTransition = {
                when (initialState.destination.route) {
                    BottomNavItem.Characters.screenRoute ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(transitionAnimationDuration)
                        )

                    BottomNavItem.Random.screenRoute -> {
                        scaleIn(animationSpec = tween(transitionAnimationDuration))
                    }
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    BottomNavItem.Characters.screenRoute ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(transitionAnimationDuration)
                        )

                    BottomNavItem.Random.screenRoute -> {
                        scaleOut(animationSpec = tween(transitionAnimationDuration))
                    }
                    else -> null
                }
            }
        ) { backstackEntry ->
            val characterJson = backstackEntry.arguments
                ?.getString(CharacterDetailDestination.CHARACTER_ARG)

            CharacterSheetScreen(
                characterDetailState = CharacterDetailState(
                    requireNotNull(
                        fromJsonStringToCharacter(requireNotNull(characterJson))
                    )
                )
            )
        }
    }
}

@Composable
fun RenderBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Random,
        BottomNavItem.Characters,
    )

    BottomNavigation(
        backgroundColor = Color.DarkGray,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.screenRoute
                } == true,
                onClick = {
                    navController.navigate(screen.screenRoute) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

fun navigateToCharacter(
    character: Character,
    navController: NavController
) {
    val route = CharacterDetailDestination.getRouteWithArgument(toJsonStringFromCharacter(character))
    navController.navigate(route)
}