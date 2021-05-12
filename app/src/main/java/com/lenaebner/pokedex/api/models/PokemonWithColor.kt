package com.lenaebner.pokedex.api.models

data class PokemonWithColor(
    val name: String,
    val id: Long,
    val sprite: String,
    val types: List<String>,
    val color: String,
    val onClick: () -> Unit
)