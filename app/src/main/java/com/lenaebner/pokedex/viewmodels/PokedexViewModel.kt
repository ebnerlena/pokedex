package com.lenaebner.pokedex.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.PokedexScreenState
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor
import kotlinx.coroutines.*


class PokedexViewModel: ViewModel() {

    private val _uiState = MutableLiveData<PokedexScreenState>()
    val uiState : LiveData<PokedexScreenState> = _uiState
    private var _pokemonsWithColor: MutableList<PokemonWithColor> = mutableListOf()

    fun createContentState(pokemons: MutableList<PokemonWithColor>) {

        _pokemonsWithColor = pokemons

        _uiState.postValue(
            PokedexScreenState.Content(
                pokemonsWithColor = pokemons
            )
        )
    }

    //TODO: increment loading after first 20 have been loaded to load it dynamically and decrease laoding time

    fun fetchPokemons(offset: Int=0, limit: Int=25) {

        _uiState.postValue(PokedexScreenState.Loading)

        var pokemonsWithColor: MutableList<PokemonWithColor> = mutableListOf()

        viewModelScope.launch {

            try {

                val pokemonsPreview = withContext(Dispatchers.IO) {
                    ApiController.pokeApi.getPokemons(offset = offset,limit = limit)
                }
                val pokemons = pokemonsPreview.results.map {
                    async {
                        withContext(Dispatchers.IO){
                            ApiController.pokeApi.getPokemon(it.name)
                        }
                    }
                }.awaitAll()

                pokemons.forEach { p ->
                    pokemonsWithColor.add(
                        PokemonWithColor(
                            p,
                            withContext(Dispatchers.IO){
                                ApiController.pokeApi.getPokemonColor(p.id)
                            }
                        )
                    )
                }

                createContentState(pokemonsWithColor)

            } catch (exception: Throwable) {

                _uiState.postValue(
                    PokedexScreenState.Error(
                        message = exception.message ?: "An error occured when trying to fetch pokemons",
                        retry = { fetchPokemons() }
                    )
                )
            }
        }
    }
}