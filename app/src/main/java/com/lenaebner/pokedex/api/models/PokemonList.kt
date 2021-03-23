package com.lenaebner.pokedex.api.models

data class PokemonList (
    val count: Int = 0,
    val results: List<PokemonPreview>
)
