package com.lenaebner.pokedex.api.models

data class Pokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprites: PokemonSprite? = null,
    val height: Int = 0,
    val weight: Int = 0,
    val moves: List<Move> = emptyList(),
    val types: List<Type> = emptyList(),
    val stats: List<Stat> = emptyList(),
    val species: Species = Species(),
    val base_experience: Int = 0
)
