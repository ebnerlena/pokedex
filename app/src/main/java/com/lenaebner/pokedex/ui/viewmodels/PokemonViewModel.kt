package com.lenaebner.pokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.repository.*
import com.lenaebner.pokedex.ui.screenstates.PokemonScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PokemonScreenAction {
    object NavigateBack : PokemonScreenAction()
    data class pokemonClicked(val destination: String) : PokemonScreenAction()
}

@HiltViewModel
class PokemonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _actions = Channel<PokemonScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = MutableStateFlow<PokemonScreenState>(PokemonScreenState.Loading)
    val uiState: StateFlow<PokemonScreenState> = _uiState

    init {
        val id = savedStateHandle["pokemonId"] ?: 25
        val speciesId = savedStateHandle["speciesId"] ?: 10

        viewModelScope.launch {
            pokemonRepository.getPokemon(id.toLong(), speciesId.toLong()).collect { pokemon ->

                pokemon.species.evolvingPokemons.map { p ->

                        EvolvingPokemons(
                            from = UiBasicPokemon(
                                name = p.from.name,
                                species = p.from.species,
                                sprite = p.from.species,
                                onClick = {
                                    viewModelScope.launch {
                                        _actions.send(
                                            PokemonScreenAction.pokemonClicked("pokemon/${p.from.id}?speciesId=${pokemon.species.id}")
                                        )
                                    }
                                }
                            ),
                            to = UiBasicPokemon(
                                name = p.to.name,
                                species = p.to.species,
                                sprite = p.to.species,
                                onClick = {
                                    viewModelScope.launch {
                                        _actions.send(
                                            PokemonScreenAction.pokemonClicked("pokemon/${p.to.id}?speciesId=${pokemon.species.id}")
                                        )
                                    }
                                }
                            ),
                            trigger = p.trigger,
                            id = p.id
                        )
                    }
                _uiState.emit(
                    PokemonScreenState.Content(
                        pokemon = pokemon.pokemon,
                        species = pokemon.species,
                        evolutionChainPokemons = pokemon.species.evolvingPokemons,
                        backClicked = { viewModelScope.launch { _actions.send(PokemonScreenAction.NavigateBack) } },
                    )
                )
            }
        }
    }
}