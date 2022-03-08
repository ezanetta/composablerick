package com.ezanetta.composablerick.domain.entity

import androidx.compose.ui.graphics.Color


enum class Status {
    Alive {
        override val color = Color.Green
    },
    Dead {
        override val color = Color.Red
    },

    unknown {
        override val color = Color.Yellow
    };

    abstract val color: Color
}