package com.lenaebner.pokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.repository.pokemon.EvolvingPokemons
import com.lenaebner.pokedex.repository.pokemon.PokemonRepository
import com.lenaebner.pokedex.repository.pokemon.SinglePokemonComplete
import com.lenaebner.pokedex.repository.pokemon.UiBasicPokemon
import com.lenaebner.pokedex.ui.screenstates.PokemonScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PokemonScreenAction {
    object NavigateBack : PokemonScreenAction()
    data class PokemonClicked(val destination: String) : PokemonScreenAction()
}

@HiltViewModel
class PokemonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _actions = Channel<PokemonScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val id = savedStateHandle["pokemonId"] ?: 25
    val speciesId = savedStateHandle["speciesId"] ?: 10

    val uiState: StateFlow<PokemonScreenState> = pokemonRepository.getPokemon(id= id.toLong(), speciesId = speciesId.toLong()).map {
        it.asContentScreenState()
    }.stateIn(viewModelScope, SharingStarted.Lazily, PokemonScreenState.Loading)

    private fun SinglePokemonComplete.asContentScreenState() = PokemonScreenState.Content(
        pokemon = pokemon,
        species = species,
        evolutionChainPokemons = species.evolvingPokemons.map { p ->

            EvolvingPokemons(
                from = UiBasicPokemon(
                    name = p.from.name,
                    species = p.from.species,
                    sprite = p.from.sprite,
                    speciesId = p.from.speciesId,
                    onClick = {
                        viewModelScope.launch {
                            _actions.send(
                                PokemonScreenAction.PokemonClicked("pokemon/${p.from.id}?speciesId=${p.from.speciesId}")
                            )
                        }
                    }
                ),
                to = UiBasicPokemon(
                    name = p.to.name,
                    species = p.to.species,
                    sprite = p.to.sprite,
                    speciesId = p.to.speciesId,
                    onClick = {
                        viewModelScope.launch {
                            _actions.send(
                                PokemonScreenAction.PokemonClicked("pokemon/${p.to.id}?speciesId=${p.to.speciesId}")
                            )
                        }
                    }
                ),
                trigger = p.trigger,
                id = p.id
            )
        },
        backClicked = { viewModelScope.launch { _actions.send(PokemonScreenAction.NavigateBack) } },
    )
}