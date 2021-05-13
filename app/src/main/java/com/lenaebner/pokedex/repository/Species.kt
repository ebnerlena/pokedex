package com.lenaebner.pokedex.repository

import androidx.room.PrimaryKey
import com.lenaebner.pokedex.api.models.ApiSpecies
import com.lenaebner.pokedex.api.models.ColorName
import com.lenaebner.pokedex.api.models.EggGroup
import com.lenaebner.pokedex.db.entities.DbSpecies
import com.lenaebner.pokedex.db.entities.DbSpeciesEggGroup

data class Species(
    val id: Int = 0,
    val name: String = "",
    val color: String = "",
    val egg_groups: List<String> = emptyList(),
    val evolvingPokemons: List<EvolvingPokemons> = emptyList(),
    val flavor_text_entry: String = "",
    val genera: String = "",
)

fun ApiSpecies.asDbSpecies() = DbSpecies(
    speciesId = id,
    name = name,
    color = color.name,
    description = flavor_text_entries[7].flavor_text.replace("[\n\r]".toRegex(), " ") ,
    genera = genera[7].genus
)

fun DbSpecies.asSpecies() = Species(
    name = name,
    id = speciesId,
    color = color,
    flavor_text_entry = description,
    genera = genera
)

fun EggGroup.asDbSpeciesEggGroup() = DbSpeciesEggGroup(
    name = name,
    eggGroupId = url.split("/")[6].toInt()
)