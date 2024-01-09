package com.bashirli.playex.presentation.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val PurpleDA = Color(0x96D622DA)
val Pink40 = Color(0xFF7D5260)
val Pink29 = Color(0xFF110929)
val Pink63 = Color(0xFF251163)

val PinkFE = Color(0xFF8D1CFE)
val BlueED = Color(0xFF0038ED)

val White99 = Color(0x99FFFFFF)


val GradientBackground = Brush.linearGradient(
    0.0f to BlueED,
    1.0f to PinkFE
)


val GradientIndicator = Brush.horizontalGradient(
    0.0f to PinkFE,
    0.5f to PinkFE,
    1.0f to BlueED,
    startX = 0.0f,
    endX = 100.0f
)
