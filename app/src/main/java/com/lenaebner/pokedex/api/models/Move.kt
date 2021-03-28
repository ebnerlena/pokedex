package com.lenaebner.pokedex.api.models

data class Move (
    val move: MoveDetail
)
data class MoveDetail(
    val name: String,
    val url: String
)