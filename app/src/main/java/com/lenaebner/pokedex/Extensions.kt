package com.lenaebner.pokedex

import android.graphics.Color
import com.lenaebner.pokedex.ui.theme.*

fun String.asPokeColor() = when(this) {

    "yellow" -> pokeYellow
    "blue" -> pokeBlue
    "red" -> pokeRed
    "green" -> pokeGreen
    "purple" -> pokeLila
    "brown" -> pokeBrown
    "pink" -> pokePink
    else -> pokeGrey
}
