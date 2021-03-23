package com.lenaebner.pokedex.api.models

data class Pokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprites: PokemonSprite? = null
)