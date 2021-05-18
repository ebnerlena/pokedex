package com.lenaebner.pokedex.repository.pokemon

import com.lenaebner.pokedex.db.entities.DbPokemon

data class Pokemon(
    val name: String = "Pikachu",
    val id: Int = 0,
    val sprite: String = "",
    val description: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val abilities: List<String>? = null,
    val types: List<String>? = null,
    val stats: List<UiStat>? = null,
    val speciesId: Int = 0
)

data class SinglePokemonComplete(
    val pokemon: Pokemon,
    val species: Species,
    //val evolvingPokemons: EvolvingPokemons
)

fun DbPokemon.asPokemon() = Pokemon(
    name = name,
    id = pokemonId,
    sprite = sprite,
    description = description,
    height = height,
    weight = weight,
    abilities = null,
    types = null,
    stats = null,
)



