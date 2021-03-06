package com.lenaebner.pokedex.repository.pokemon


data class Pokemon(
    val name: String = "Pikachu",
    val id: Int = 0,
    val sprite: String = "",
    val description: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val abilities: List<String>? = null,
    val types: List<String>? = null,
    val stats: List<UiStat>? = null,
    val speciesId: Int = 0
)

data class SinglePokemonComplete(
    val pokemon: Pokemon,
    val species: Species,
)




