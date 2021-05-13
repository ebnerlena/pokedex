package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.db.entities.BasicPokemon
import com.lenaebner.pokedex.db.entities.DbEvolvingPokemons


data class EvolvingPokemons(
    val id: Int,
    val from: UiBasicPokemon,
    val to: UiBasicPokemon,
    val trigger: String
)

data class UiBasicPokemon(
    val id: Int = 1,
    val name: String = "Pikachu",
    val sprite: String,
    val species: String,
    val onClick: () -> Unit
)

data class EvolvingPokemon(
    val id: Int = 0,
    val name: String = "Pikachu",
    val sprite: String,
    val species: String,
    val onClick: () -> Unit
)

fun EvolvingPokemons.asDbEvolvingPokemon() = DbEvolvingPokemons(
    from = from.asBasicPokemon() ,
    to = to.asBasicPokemon(),
    trigger = trigger
)

fun DbEvolvingPokemons.asEvolvingPokemon() = EvolvingPokemons(
    from = from.asUiBasicPokemon(),
    to = to.asUiBasicPokemon(),
    trigger = trigger,
    id = evolvingPokemonId
)

fun BasicPokemon.asUiBasicPokemon() = UiBasicPokemon(
    id = basicPokemonId,
    name = name,
    species = speciesName,
    sprite = sprite,
    onClick = {}
)

fun UiBasicPokemon.asBasicPokemon() = BasicPokemon(
    basicPokemonId = id,
    name = name,
    speciesName = name,
    sprite = sprite,
)