package com.lenaebner.pokedex.api.models

data class ApiPokemonList (
    val count: Int = 0,
    val results: List<PokemonPreview>,
    val next: String?
)
