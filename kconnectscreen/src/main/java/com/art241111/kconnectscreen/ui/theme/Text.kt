package com.art241111.kconnectscreen.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextHeader(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color = Color.White,
    fontFamily: FontFamily? = MaterialTheme.typography.h1.fontFamily,
    fontSize: TextUnit = MaterialTheme.typography.h1.fontSize
) {
    Text(
        modifier = modifier,
        text = stringResource(id = text),
        style = MaterialTheme.typography.h1,
        fontFamily = fontFamily,
        color = color,
        fontSize = fontSize,
        textAlign = TextAlign.Center
    )
}
