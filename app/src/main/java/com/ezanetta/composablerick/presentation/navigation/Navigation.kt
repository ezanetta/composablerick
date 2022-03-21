package com.ezanetta.composablerick.presentation.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ezanetta.composablerick.extensions.fromJsonStringToCharacter
import com.ezanetta.composablerick.presentation.characters.compose.CharactersScreen
import com.ezanetta.composablerick.presentation.characters.viewmodel.GetAllCharactersViewModel
import com.ezanetta.composablerick.presentation.charactersheet.compose.CharacterSheetScreen
import com.ezanetta.composablerick.presentation.charactersheet.model.CharacterSheetState
import com.ezanetta.composablerick.presentation.randomcharacter.compose.RandomCharacterScreen
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import com.ezanetta.composablerick.presentation.search.SearchCharacterScreen
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavigationGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    getCharacterViewModel: GetCharacterViewModel,
    getAllCharactersViewModel: GetAllCharactersViewModel
) {

    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
        NavHost(
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
                    handleEvent = getCharacterViewModel::handleEvent
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

            composable(BottomNavItem.Search.screenRoute) {
                SearchCharacterScreen()
            }

            bottomSheet(route = CharacterSheetDestination.ROUTE_WITH_ARG) { backstackEntry ->
                val characterJson = backstackEntry.arguments
                    ?.getString(CharacterSheetDestination.CHARACTER_ARG)

                CharacterSheetScreen(
                    characterSheetState = CharacterSheetState(
                        requireNotNull(
                            fromJsonStringToCharacter(requireNotNull(characterJson))
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun RenderBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Random,
        BottomNavItem.Characters,
        BottomNavItem.Search,
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