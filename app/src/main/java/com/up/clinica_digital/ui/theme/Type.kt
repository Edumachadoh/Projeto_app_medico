package com.up.clinica_digital.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.up.clinica_digital.R

val Sora = FontFamily(Font(R.font.sora_extra_bold))
val Geist = FontFamily(Font(R.font.geist_regular))

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Sora,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Sora,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Geist,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Geist,
        fontSize = 14.sp
    )
)