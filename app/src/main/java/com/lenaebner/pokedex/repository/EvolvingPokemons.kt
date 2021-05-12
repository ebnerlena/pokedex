package com.lenaebner.pokedex.repository


data class EvolvingPokemons(
    val from: BasicPokemon,
    val to: BasicPokemon,
    val trigger: String
)

data class BasicPokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprites: String,
    val species: Species = Species(),
    val onClick: () -> Unit
)