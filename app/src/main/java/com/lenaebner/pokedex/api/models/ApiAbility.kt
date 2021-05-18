package com.lenaebner.pokedex.api.models

data class ApiAbility(
    val ability: AbilityDetail
)

data class AbilityDetail(
    val name: String,
    val url: String
)