package com.lenaebner.pokedex.ui.pokedex


data class PokemonWithColor(
    val name: String,
    val id: Long,
    val sprite: String,
    val types: List<String>,
    val color: String,
    val onClick: () -> Unit
)

