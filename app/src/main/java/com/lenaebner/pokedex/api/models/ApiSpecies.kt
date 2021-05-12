package com.lenaebner.pokedex.api.models

data class PokemonSpecies(
    val name: String = "",
    val url: String = ""
)

data class ApiSpecies(
    val id: Int = 0,
    val name: String = "",
    val color: ColorName = ColorName(),
    val egg_groups: List<EggGroup> = emptyList(),
    val evolution_chain: ApiEvolutionChain = ApiEvolutionChain(),
    val flavor_text_entries: List<ApiFlavorTextEntry> = emptyList(),
    val genera: List<GeneraEntry> = emptyList(),
)

data class ColorName(
    val name: String = "red"
)

data class EggGroup(
    val name: String = "",
)

data class GeneraEntry(
    val genus: String = ""
)