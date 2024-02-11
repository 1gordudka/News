package com.igordudka.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BigTitleText(
    text: String
) {

    Text(text = text, fontWeight = FontWeight.Medium, color = Color.White,
        fontSize = 50.sp,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun MediumTitleText(
    text: String,
    shortLength: Boolean = false
) {
    
    Text(text = text, color = Color.White, fontSize = 25.sp,
        maxLines = if (shortLength) 1 else 1000,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun SmallText(
    text: String,
    shortLength: Boolean = false,
    isLight: Boolean = false
) {

    Text(text = text, color = Color.White.copy(alpha = if (isLight) 1f else 0.54f), fontSize = 13.sp,
        maxLines = if (shortLength) 1 else 1000, modifier = Modifier.padding(if (shortLength) 0.dp else 8.dp)
            .padding(start = if (shortLength) 8.dp else 0.dp))
}

@Composable
fun MediumText(
    text: String,
) {

    Text(text = text, color = Color.White, fontSize = 17.sp, modifier = Modifier.padding(8.dp))
}

@Composable
fun DateText(
    text: String
) {

    Text(text = text, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun LinkText(
    uri: String
) {

    val hyperlinkText = "Ссылка"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(textDecoration = TextDecoration.None, color = Color(0xFF3F97FF), fontSize = 15.sp)) {
            append(hyperlinkText)
            addStringAnnotation(
                tag = "URL",
                annotation = uri,
                start = length - hyperlinkText.length,
                end = length
            )
        }
    }
    val uriHandler = LocalUriHandler.current
    Row(Modifier.padding(8.dp)) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotation ->
                        uriHandler.openUri(annotation.item)
                    }
            }
        )
    }
}

@Composable
fun Screen(
    content: @Composable () -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(backColor)
            .systemBarsPadding()
            .padding(16.dp), horizontalAlignment = Alignment.Start) {
        content()
    }
}

