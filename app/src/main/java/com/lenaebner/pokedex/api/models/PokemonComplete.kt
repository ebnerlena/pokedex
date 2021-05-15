package com.lenaebner.pokedex.api.models

data class PokemonComplete(
    val pokemon: Pokemon,
    val species: PokemonSpecies,
)

data class PokemonSpeciesChain(
    val pokemon: Pokemon = Pokemon(),
    val species: PokemonSpecies? = PokemonSpecies(),
    val evolutionChainPokemons: MutableList<EvolvingPokemons> = mutableListOf()
)
