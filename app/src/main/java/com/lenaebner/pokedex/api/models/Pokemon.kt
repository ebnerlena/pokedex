package com.lenaebner.pokedex.api.models

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: PokemonSprite
)