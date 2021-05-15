package com.lenaebner.pokedex.ui.screenstates

import com.lenaebner.pokedex.repository.pokemon.EvolvingPokemons
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.repository.pokemon.Species

sealed class PokemonScreenState {

    object Loading: PokemonScreenState()
    data class Content(
        val pokemon: Pokemon,
        val species: Species?,
        val evolutionChainPokemons: List<EvolvingPokemons> = emptyList(),
        val backClicked: () -> Unit
    ) : PokemonScreenState()
    data class Error(val message: String, val retry: () -> Unit): PokemonScreenState()
}