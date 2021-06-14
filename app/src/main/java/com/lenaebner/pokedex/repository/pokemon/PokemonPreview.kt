package com.lenaebner.pokedex.repository.pokemon

import com.lenaebner.pokedex.db.entities.DbPokemonPreview
import com.lenaebner.pokedex.db.entities.PokemonPreviewWithTypes

data class SearchPokemonPreview(
    val name: String,
    val id: Long,
    val speciesId: Long
)

fun DbPokemonPreview.asSearchPokemonPreview() = SearchPokemonPreview(
    name = name,
    id = pokemonId,
    speciesId = speciesId
)

