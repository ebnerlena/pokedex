package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.EvolvingPokemons
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies

sealed class PokemonScreenState {

    object Loading: PokemonScreenState()
    data class Content(
        val pokemon: Pokemon,
        val species: PokemonSpecies?,
        val evolutionChainPokemons: MutableList<EvolvingPokemons> = mutableListOf(),
        val backClicked: () -> Unit
    ) : PokemonScreenState()
    data class Error(val message: String, val retry: () -> Unit): PokemonScreenState()
}