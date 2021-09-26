package com.steve28.stedit.highlighter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

interface Highlighter: VisualTransformation {
    val keyword: List<Keyword>

    override fun filter(
        text: AnnotatedString
    ) = run {
        val txt = text.text
        TransformedText(
            process(txt, getHighlightOption(txt, keyword)),
            OffsetMapping.Identity
        )
    }

    fun process(text: String, option: List<HighlightOption>): AnnotatedString {
        val builder = AnnotatedString.Builder()
        var index = 0

        option.forEach {
            val (start, end, color) = it
            if (index > start) return@forEach
            builder.color(Color.White, text.substring(index, start))
            builder.color(color, text.substring(start, end))
            index = end
        }
        builder.color(Color.White, text.substring(index, text.length))

        return builder.toAnnotatedString()
    }

    companion object {
        private fun AnnotatedString.Builder.color(
            color: Color, text: String
        ) { this.withStyle(style = SpanStyle(color = color)) { append(text) } }

        fun getHighlightOption(text: String, keyword: List<Keyword>)
            = keyword.map { it.find(text) }.flatten().sortedBy { it.start*100 - it.priority }
    }
}