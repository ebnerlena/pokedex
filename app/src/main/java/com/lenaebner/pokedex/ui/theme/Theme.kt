package com.lenaebner.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColorDark,
    primaryVariant = accentColorDark,
    secondary = secondaryColorDark,
    background = backgroundColorDark,
    onSecondary = Color.White,
    onPrimary =  Color.Black,
    secondaryVariant = textColorDark,
    surface = backgroundColorDark
)

private val LightColorPalette = lightColors(
    primary = primaryColorLight,
    primaryVariant = accentColorLight,
    secondary = secondaryColorLight,
    background = backgroundColorLight,
    onPrimary = primaryColorLight,
    onSecondary = Color.White,
    surface = backgroundColorLight,
    onBackground = backgroundColorLight,
    secondaryVariant = textColorLight


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {

    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = PokedexTypography,
        shapes = RoundedCornerShapes,
        content = content
    )
}