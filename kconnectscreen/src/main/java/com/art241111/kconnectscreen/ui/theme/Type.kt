package com.art241111.kconnectscreen.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.art241111.kconnectscreen.R

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = Font(R.font.geometria_medium).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h1 = TextStyle(
        fontFamily = Font(R.font.geometria_heavy).toFontFamily(),
        fontWeight = FontWeight.Thin,
        fontSize = 36.sp
    ),

    h2 = TextStyle(
        fontFamily = Font(R.font.geometriabold).toFontFamily(),
        fontWeight = FontWeight.Thin,
        fontSize = 24.sp
    ),

    button = TextStyle(
        fontFamily = Font(R.font.geometria_extra_bold).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    defaultFontFamily = Font(R.font.geometria_medium).toFontFamily()

        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
