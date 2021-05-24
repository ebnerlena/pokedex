package com.lenaebner.pokedex.repository.pokemon

import com.lenaebner.pokedex.api.models.ApiSpecies
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
    description = flavor_text_entries.find{entry -> entry.language.name == "en"}?.flavor_text?.replace("[\n\r]".toRegex(), " ") ?: "" ,
    genera = genera.find{g -> g.language.name == "en"}?.genus ?: "Genera"
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