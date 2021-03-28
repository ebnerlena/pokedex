package com.lenaebner.pokedex.api.models

data class Species(
    val name: String = "",
    val url: String = ""
)

data class PokemonSpecies(
    val color: Color,
    val egg_groups: List<EggGroup>,
    val evolution_chain: EvolutionChain,
    val flavor_text_entries: List<FlavorTextEntry>
)

data class Color(
    val name: String
)

data class EggGroup(
    val name: String,
    val url: String
)

data class FlavorTextEntry(
    val flavor_text: String
)