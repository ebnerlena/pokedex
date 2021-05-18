package com.lenaebner.pokedex.api.models


data class ApiPokemon(
    val id: Long = 1,
    val name: String = "Pikachu",
    val sprites: ApiPokemonSprite? = null,
    val height: Int = 0,
    val weight: Int = 0,
    val moves: List<PokemonMove> = emptyList(),
    val types: List<ApiType> = emptyList(),
    val abilities: List<ApiAbility> = emptyList(),
    val stats: List<ApiStat> = emptyList(),
    val species: PokemonSpecies = PokemonSpecies(),
    val base_experience: Int = 0
)

data class PokemonMove(
    val move: MovePreview
)

data class PokemonPreview(
    val name: String = "",
    val url: String = ""
)

data class PokemonColor(
    val color: ColorName
)