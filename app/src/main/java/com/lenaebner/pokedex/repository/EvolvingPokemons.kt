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
    val species: String,
    val onClick: () -> Unit
)

data class EvolvingPokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprites: String,
    val species: String,
    val onClick: () -> Unit
)