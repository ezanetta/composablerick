package com.ezanetta.composablerick.presentation.randomcharacter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ezanetta.composablerick.domain.entity.Character
import com.ezanetta.composablerick.presentation.randomcharacter.compose.RandomCharacterScreen
import com.ezanetta.composablerick.presentation.randomcharacter.model.RandomCharacterState
import com.ezanetta.composablerick.presentation.randomcharacter.viewmodel.GetCharacterViewModel
import com.ezanetta.composablerick.presentation.ui.theme.ComposableRickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomCharacterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val getCharacterViewModel: GetCharacterViewModel = viewModel()

            ComposableRickTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RandomCharacterScreen(
                        randomCharacterState = getCharacterViewModel
                            .uiState
                            .collectAsState()
                            .value
                    )
                }
            }
        }
    }
}