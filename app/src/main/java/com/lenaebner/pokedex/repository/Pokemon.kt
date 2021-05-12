package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.db.entities.DbPokemon

data class Pokemon(
    val name: String,
    val id: Long,
    val sprite: String,
    val description: String,
    val height: Int,
    val weight: Int
)

data class SinglePokemonComplete(
    val pokemon: Pokemon,
    //val species: PokemonSpecies,
    //val evolvingPokemons: EvolvingPokemons
)

fun DbPokemon.asPokemon() = Pokemon(
    name = name,
    id = pokemonId.toLong(),
    sprite = sprite,
    description = description,
    height = height,
    weight = weight,
)