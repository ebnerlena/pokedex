package com.lenaebner.pokedex.api.models

data class ApiMoveList(
    val count: Int = 0,
    val results: List<MovePreview>
)