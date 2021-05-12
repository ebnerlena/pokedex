package com.lenaebner.pokedex.repository

import androidx.room.PrimaryKey
import com.lenaebner.pokedex.api.models.ApiSpecies
import com.lenaebner.pokedex.api.models.ColorName
import com.lenaebner.pokedex.db.entities.DbSpecies

data class Species(
    val name: String = "",
    val color: ColorName = ColorName(),
    //val egg_groups: List<String> = emptyList(),
    //val evolution_chain: ApiEvolutionChain = ApiEvolutionChain(),
    val flavor_text_entries: String = "",
    val genera: String = "",
)

fun ApiSpecies.asDbSpecies() = DbSpecies(
    speciesId = id,
    name = name,
    color = color.name,
    description = flavor_text_entries[7].flavor_text.replace("[\n\r]".toRegex(), " ") ,
    genera = genera[7].genus
)