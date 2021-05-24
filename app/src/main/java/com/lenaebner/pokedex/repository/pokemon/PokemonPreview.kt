package com.lenaebner.pokedex.repository.pokemon

import com.lenaebner.pokedex.db.entities.DbPokemonPreview
import com.lenaebner.pokedex.db.entities.PokemonPreviewWithTypes

data class PokemonPreview(
    val id: Long,
    val name: String,
    val sprite: String,
    val color: String,
    val types: List<String> = emptyList(),
    val speciesId: Long,
)

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

fun PokemonPreviewWithTypes.asPokemonPreview() = PokemonPreview(
    id = pokemon.pokemonId,
    name = pokemon.name,
    sprite = pokemon.sprite,
    color = pokemon.color,
    types = types.map { it.name },
    speciesId = pokemon.speciesId
)
