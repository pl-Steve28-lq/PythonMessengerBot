package com.steve28.stedit.highlighter

import androidx.compose.ui.graphics.Color

data class HighlightOption(
    val start: Int,
    val end: Int,
    val color: Color,
    val priority: Int
)
