package com.lenaebner.pokedex.api.models

data class EvolutionChain(
    val url: String = ""
)

data class EvolutionChainDetails(
    val chain: Chain
)

data class EvolutionChainDetail(
    val min_level: Int?,
    val min_happiness: Int?,
    val trigger: Trigger,
    val item: EvolutionItem?
)

data class Chain(
    val evolves_to: List<EvolveEntry> = emptyList(),
    val species: Species? = null,
    val is_baby: Boolean = false
)

data class Trigger(
    val name: String
)

data class EvolveEntry(
    val species: Species,
    val evolution_details: List<EvolutionChainDetail> = emptyList(),
    val evolves_to: List<EvolveEntry> = emptyList(),
    val is_baby: Boolean = false
)

data class EvolutionItem(
    val name: String = ""
)
