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

    init {
        fetchPokemons(0,10)
        fetchPokemons(10,10)
        fetchPokemons(20,20)
        fetchPokemons(40,20)
    }

    fun createContentState() {

        _pokemonsWithColor.sortBy { p -> p.pokemon?.id }

        _uiState.postValue(
            PokedexScreenState.Content(
                pokemonsWithColor = _pokemonsWithColor
            )
        )
    }

    fun fetchPokemons(offset: Int=0, limit: Int=20) {

        _uiState.postValue(PokedexScreenState.Loading)

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
                    _pokemonsWithColor.add(
                        PokemonWithColor(
                            p,
                            withContext(Dispatchers.IO){
                                ApiController.pokeApi.getPokemonColor(p.id)
                            }
                        )
                    )
                }

                createContentState()

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