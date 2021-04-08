package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.api.models.EvolutionChainDetails
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies

sealed class PokemonScreenState {

    object loading: PokemonScreenState()
    data class content(
        val pokemon: Pokemon,
        val species: PokemonSpecies,
        val evolutionChain: EvolutionChainDetails
        ) : PokemonScreenState()
    data class error(val message: String): PokemonScreenState()
}

class PokeViewModel(name: String): ViewModel() {

    val state  = liveData<PokemonScreenState> {

        val pokemon = ApiController.pokeApi.getPokemon(name)
        val species = ApiController.pokeApi.getPokemonSpecies(pokemon.id)
        val id = species.evolution_chain.url.split("/")[6].toInt()
        val evolutionChain = ApiController.pokeApi.getEvolutionChain(id)

        emit(PokemonScreenState.content(
            pokemon = pokemon, species = species, evolutionChain = evolutionChain
        ))
    }
}