package com.lenaebner.pokedex.api.models

data class EvolvingPokemons(
    val from: BasicPokemon,
    val to: BasicPokemon,
    val trigger: String
)

data class BasicPokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprites: PokemonSprite? = null,
    val species: Species = Species(),
    val onClick: () -> Unit
)