package com.lenaebner.pokedex.api.models

data class EvolvingPokemons(
    val from: Pokemon,
    val to: Pokemon,
    val trigger: String
)