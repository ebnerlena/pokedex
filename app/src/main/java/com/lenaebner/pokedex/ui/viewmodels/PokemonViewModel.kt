package com.lenaebner.pokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.repository.PokemonRepository
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
            pokemonRepository.getPokemon(id.toLong(), speciesId.toLong()).collect {
                _uiState.emit(
                    PokemonScreenState.Content(
                        pokemon = it.pokemon,
                        species = it.species,
                        evolutionChainPokemons = it.species.evolvingPokemons,
                        backClicked = { viewModelScope.launch { _actions.send(PokemonScreenAction.NavigateBack) } },
                    )

                )
            }

        }
    }
}