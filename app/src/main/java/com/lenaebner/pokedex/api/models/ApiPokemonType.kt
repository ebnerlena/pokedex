package com.lenaebner.pokedex.api.models

data class ApiType(
    val type: TypeDetail
)
data class TypeDetail (
    val name: String,
    val url: String
)