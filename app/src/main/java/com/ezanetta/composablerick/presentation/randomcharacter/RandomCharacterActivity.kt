package com.ezanetta.composablerick.presentation.randomcharacter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ezanetta.composablerick.presentation.navigation.NavigationGraph
import com.ezanetta.composablerick.presentation.navigation.RenderBottomNavigation
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomCharacterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val getCharacterViewModel: GetCharacterViewModel = viewModel()
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { RenderBottomNavigation(navController = navController) }
            ) {
                NavigationGraph(navController = navController, getCharacterViewModel)
            }
        }
    }
}