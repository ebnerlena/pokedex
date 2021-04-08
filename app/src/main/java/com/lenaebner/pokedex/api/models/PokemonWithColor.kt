package com.lenaebner.pokedex.api.models

data class PokemonColor(
    val color: ColorName
)

data class PokemonWithColor(
    val pokemon: Pokemon?,
    val color: PokemonColor
)