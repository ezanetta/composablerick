package com.ezanetta.composablerick.presentation.randomcharacter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezanetta.composablerick.presentation.randomcharacter.compose.RandomCharacterScreen
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomCharacterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val getCharacterViewModel: GetCharacterViewModel = viewModel()
            RandomCharacterScreen(
                randomCharacterState = getCharacterViewModel
                    .uiState
                    .collectAsState()
                    .value,
                handleEvent = getCharacterViewModel::handleEvent
            )
        }
    }
}