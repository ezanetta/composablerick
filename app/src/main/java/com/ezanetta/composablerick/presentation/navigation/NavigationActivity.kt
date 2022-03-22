package com.ezanetta.composablerick.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezanetta.composablerick.presentation.characters.viewmodel.GetAllCharactersViewModel
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val getCharacterViewModel: GetCharacterViewModel = viewModel()
            val getAllCharactersViewModel: GetAllCharactersViewModel = viewModel()
            val navController = rememberAnimatedNavController()

            Scaffold(
                bottomBar = { RenderBottomNavigation(navController = navController) }
            ) { innerPadding ->
                NavigationGraph(
                    innerPadding = innerPadding,
                    navController = navController,
                    getCharacterViewModel = getCharacterViewModel,
                    getAllCharactersViewModel = getAllCharactersViewModel
                )
            }
        }
    }
}