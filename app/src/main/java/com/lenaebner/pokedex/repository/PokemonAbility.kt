package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.models.ApiAbility
import com.lenaebner.pokedex.db.entities.DbPokemonAbility


fun ApiAbility.asDbPokemonAbility() = DbPokemonAbility(
    name = ability.name,
    abilityId = ability.url.split("/")[6].toLong()
)