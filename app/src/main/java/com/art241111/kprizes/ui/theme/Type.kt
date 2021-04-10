package com.art241111.kprizes.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.art241111.kprizes.R

val typography = Typography(
    body1 = TextStyle(
        fontFamily = Font(R.font.sansation_bold).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    h1 = TextStyle(
        fontFamily = Font(R.font.sansation_regular).toFontFamily(),
        fontWeight = FontWeight.Thin,
        fontSize = 90.sp,
        textAlign = TextAlign.Center,
        color = Red700
    ),

    h2 = TextStyle(
        fontFamily = Font(R.font.sansation_regular).toFontFamily(),
        fontWeight = FontWeight.Thin,
        fontSize = 24.sp
    ),

    button = TextStyle(
        fontFamily = Font(R.font.sansation_regular).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    defaultFontFamily = Font(R.font.sansation_regular).toFontFamily()
)
