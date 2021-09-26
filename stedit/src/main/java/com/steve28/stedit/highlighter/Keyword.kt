package com.steve28.stedit.highlighter

import androidx.compose.ui.graphics.Color

data class Keyword(val regex: Regex, val color: Color, val priority: Int = 1) {
    fun find(text: String) =
        regex.findAll(text).toList().map {
            val r = it.range
            HighlightOption(r.first, r.last+1, color, priority)
        }

    companion object {
        fun fixed(regex: String, color: Color, priority: Int = 1)
            = Keyword("(?<!\\w)$regex(?!\\w)".toRegex(), color, priority)
    }
}