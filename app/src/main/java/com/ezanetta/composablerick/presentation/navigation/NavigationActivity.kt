package com.ezanetta.composablerick.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.ezanetta.composablerick.presentation.characters.viewmodel.GetAllCharactersViewModel
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val getCharacterViewModel: GetCharacterViewModel = viewModel()
            val getAllCharactersViewModel: GetAllCharactersViewModel = viewModel()
            val navController = rememberNavController()
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            navController.navigatorProvider += bottomSheetNavigator

            Scaffold(
                bottomBar = { RenderBottomNavigation(navController = navController) }
            ) { innerPadding ->
                NavigationGraph(
                    innerPadding = innerPadding,
                    navController = navController,
                    bottomSheetNavigator = bottomSheetNavigator,
                    getCharacterViewModel = getCharacterViewModel,
                    getAllCharactersViewModel = getAllCharactersViewModel
                )
            }
        }
    }
}