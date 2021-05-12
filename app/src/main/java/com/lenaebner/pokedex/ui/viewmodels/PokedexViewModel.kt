package com.lenaebner.pokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.api.models.PokemonWithColor
import com.lenaebner.pokedex.repository.PokedexRepository
import com.lenaebner.pokedex.ui.screenstates.PokedexScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository
): ViewModel() {

    private val _actions = Channel<PokedexScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = MutableStateFlow<PokedexScreenState>(PokedexScreenState.Loading)

    val uiState : StateFlow<PokedexScreenState> = _uiState

    init {
        viewModelScope.launch {
            pokedexRepository.getPokemons(1, 30).collect {
                val pokemons = it.map { p ->
                    PokemonWithColor(
                        name = p.name,
                        color = p.color,
                        id = p.id,
                        types = p.types,
                        sprite = p.sprite,
                        onClick = {
                            viewModelScope.launch {
                                _actions.send(PokedexScreenAction.pokemonClicked("pokemon/${p.id}"))
                            }
                        }
                    )
                }

                _uiState.emit(
                    PokedexScreenState.Content(
                        pokemonsWithColor = pokemons,
                        backClicked = { viewModelScope.launch { _actions.send(PokedexScreenAction.NavigateBack) } }
                    )
                )
            }
        }
    }

    sealed class PokedexScreenAction {
        object NavigateBack : PokedexScreenAction()
        data class pokemonClicked(val destination: String): PokedexScreenAction()
    }
}