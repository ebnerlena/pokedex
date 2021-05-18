package com.lenaebner.pokedex.viewmodels


import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.PokedexScreenState
import com.lenaebner.pokedex.ScreenStates.PokemonScreenState
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow


class PokedexViewModel: ViewModel() {

    private val _actions = Channel<PokedexScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = flow {
        emit(PokedexScreenState.Loading)
        try {
            val pokemons = fetchPokemons()
            emit(
                PokedexScreenState.Content(
                    pokemonsWithColor = pokemons,
                    backClicked = {  viewModelScope.launch { _actions.send(PokedexScreenAction.NavigateBack) } },
                )
            )
        } catch (ex: Throwable) {
            emit(
                PokedexScreenState.Error(
                    message = "An error occured when fetching the requested pokemon",
                    retry = {})
            )
        }
    }

    val uiState = _uiState.asLiveData()

    private suspend fun fetchPokemons(offset: Int=0, limit: Int=20): List<PokemonWithColor> {

        val pokemonsPreview = withContext(Dispatchers.IO) { async {
            ApiController.pokeApi.getPokemons(offset = offset,limit = limit) }
        }.await()

        var pokemons = pokemonsPreview.results.map {
            withContext(Dispatchers.IO){ async {
                    ApiController.pokeApi.getPokemon(it.name)
                }
            }
        }.awaitAll()

        return pokemons.map { p ->
            PokemonWithColor(
                pokemon = p,
                color = withContext(Dispatchers.IO) {
                    ApiController.pokeApi.getPokemonColor(p.id)
                },
                onClick = { viewModelScope.launch {
                    _actions.send(PokedexScreenAction.pokemonClicked("pokemon/${p.name}")) }
                }
            )
        }
    }

    sealed class PokedexScreenAction {
        object NavigateBack : PokedexScreenAction()
        data class pokemonClicked(val destination: String): PokedexScreenAction()
    }
}