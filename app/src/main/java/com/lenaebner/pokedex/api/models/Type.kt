package com.lenaebner.pokedex.api.models

data class Type(
    val type: TypeDetail
)
data class TypeDetail (
    val name: String,
    val url: String
)