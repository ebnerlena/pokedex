package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.models.ApiPokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor
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

fun PokemonPreviewWithTypes.asPokemonPreview() = PokemonPreview(
    id = pokemon.pokemonId,
    name = pokemon.name,
    sprite = pokemon.sprite,
    color = pokemon.color,
    types = types.map { it.name },
    speciesId = pokemon.speciesId
)
