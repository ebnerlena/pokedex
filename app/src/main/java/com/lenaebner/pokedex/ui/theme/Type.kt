package com.lenaebner.pokedex.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lenaebner.pokedex.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_medium, FontWeight.W500),
    Font(R.font.montserrat_semibold, FontWeight.W600)
)

/**
 * https://fonts.google.com/specimen/Domine
 */
private val Domine = FontFamily(
    Font(R.font.domine_regular),
    Font(R.font.domine_bold, FontWeight.Bold)
)

val PokedexTypography = Typography (
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W700,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 25.sp
    ),
    h3 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Domine,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)