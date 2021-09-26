package com.steve28.stedit

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.steve28.stedit.highlighter.Highlighter

class SteditView {
    var text = ""

    @Composable
    fun Compose(modifier: Modifier = Modifier, highlighter: Highlighter) {
        val option = Highlighter.getHighlightOption(text, highlighter.keyword)
        Text(
            text = highlighter.process(text, option),
            modifier = Modifier,
            color = Color(0xFF002240)
        )
    }
}