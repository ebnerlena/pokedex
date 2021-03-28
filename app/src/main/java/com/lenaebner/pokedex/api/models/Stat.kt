package com.lenaebner.pokedex.api.models

data class Stat (
    val base_stat: Int,
    val effort: Int,
    val stat: StatDetail
)

data class StatDetail(
    val name: String,
    val url: String
)
