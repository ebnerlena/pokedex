package com.lenaebner.pokedex.api.models


data class ApiEvolutionChain(
    val url: String = ""
)

data class ApiEvolutionChainDetails(
    val chain: Chain,
    val id: Int
)

data class EvolutionChainDetail(
    val min_level: Int?,
    val min_happiness: Int?,
    val trigger: Trigger,
    val item: EvolutionItem?
)

data class Chain(
    val evolves_to: List<EvolveEntry> = emptyList(),
    val species: ApiSpecies? = null,
    val is_baby: Boolean = false
)

data class Trigger(
    val name: String
)

data class EvolveEntry(
    val species: ApiSpecies,
    val evolution_details: List<EvolutionChainDetail> = emptyList(),
    val evolves_to: List<EvolveEntry> = emptyList(),
    val is_baby: Boolean = false
)

data class EvolutionItem(
    val name: String = ""
)