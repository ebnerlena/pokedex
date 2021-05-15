package com.lenaebner.pokedex.repository.pokemon

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
    val speciesId: Long,
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
    speciesId = speciesId,
    onClick = {}
)

fun UiBasicPokemon.asBasicPokemon() = BasicPokemon(
    basicPokemonId = id,
    name = name,
    speciesName = name,
    sprite = sprite,
    speciesId = speciesId
)