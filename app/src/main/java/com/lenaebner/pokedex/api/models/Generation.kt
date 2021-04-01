package com.lenaebner.pokedex.api.models

data class GenerationPreview(
    val name: String,
    val url: String
)

data class Generation(
    val id: Int = 1,
    val name: String ="",
    val pokemon_species : List<PokemonPreview> = emptyList()
)

data class GenerationsList (
    val count: Int = 0,
    val results: List<GenerationPreview> = emptyList()
)