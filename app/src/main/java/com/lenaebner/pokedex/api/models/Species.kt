package com.lenaebner.pokedex.api.models

import androidx.versionedparcelable.ParcelField
import androidx.versionedparcelable.VersionedParcelize

data class Species(
    val name: String = "",
    val url: String = ""
)

data class PokemonSpecies(
    val name: String = "",
    val color: ColorName = ColorName(),
    val egg_groups: List<EggGroup> = emptyList(),
    val evolution_chain: EvolutionChain = EvolutionChain(),
    val flavor_text_entries: List<FlavorTextEntry> = emptyList(),
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