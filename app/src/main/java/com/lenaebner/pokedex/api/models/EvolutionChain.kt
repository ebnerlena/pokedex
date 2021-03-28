package com.lenaebner.pokedex.api.models

data class EvolutionChain(
    val url: String
)

data class EvolutionChainDetails(
    val chain: Chain
)

data class EvolutionChainDetail(
    val min_level: Int,
    val trigger: Trigger
)

data class Chain(
    val envolves_to: EnvolveEntry?,
    val species: Species
)

data class Trigger(
    val name: String
)

data class EnvolveEntry(
    val species: Species,
    val evolution_details: EvolutionChainDetail

)