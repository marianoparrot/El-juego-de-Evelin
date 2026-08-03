package com.evelin.juego.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = WineRed,
    secondary = RoseGold,
    tertiary = SoftPink,
    background = BurgundyDark,
    surface = BurgundyMid,
    onPrimary = TextWhite,
    onSecondary = BurgundyDark,
    onBackground = TextWhite,
    onSurface = TextWhite
)

@Composable
fun ElJuegoDeEvelinTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
