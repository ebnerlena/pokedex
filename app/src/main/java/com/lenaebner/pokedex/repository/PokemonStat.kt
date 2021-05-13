package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.models.ApiStat
import com.lenaebner.pokedex.db.entities.DbPokemonStat

fun ApiStat.asDbPokemonStat(pokemonId: Long) = DbPokemonStat(
    name = stat.name,
    effort = effort,
    base_stat = base_stat,
    statPokemonId = pokemonId
)

fun DbPokemonStat.asUiStat() = UiStat(
    name = name,
    effort = effort,
    baseStat = base_stat,
)

data class UiStat(
    val name: String,
    val effort: Int,
    val baseStat: Int
)