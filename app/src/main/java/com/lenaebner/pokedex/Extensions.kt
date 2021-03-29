package com.lenaebner.pokedex

import android.graphics.Color
import com.lenaebner.pokedex.ui.theme.*

fun ConvertStringToPokeColor(color: String): androidx.compose.ui.graphics.Color {
    return when (color) {
        "yellow" -> pokeYellow
        "blue" -> pokeBlue
        "red" -> pokeRed
        "green" -> pokeGreen
        "purple" -> pokeLila
        "brown" -> pokeBrown
        "pink" -> pokePink
        else -> pokeGrey
    }
}