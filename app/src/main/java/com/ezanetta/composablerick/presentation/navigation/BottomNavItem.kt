package com.ezanetta.composablerick.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: String
) {
    object Random : BottomNavItem(
        "Random",
        Icons.Filled.Shuffle,
        "random"
    )

    object Characters : BottomNavItem(
        "Characters",
        Icons.Filled.List,
        "characters"
    )
}
