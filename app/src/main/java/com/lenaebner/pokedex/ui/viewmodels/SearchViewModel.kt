package com.lenaebner.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.lenaebner.pokedex.repository.pokemon.PokedexRepository
import com.lenaebner.pokedex.ui.screenstates.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pokedexRepository: PokedexRepository
) : ViewModel()
{
    private val _results = MutableStateFlow<SearchState>(SearchState.Loading)
    val results: StateFlow<SearchState> = _results

    fun onQueryChanged(query: String) {

        viewModelScope.launch {

            pokedexRepository.findPokemons(query).collect {
                _results.emit(
                    SearchState.Content(
                        pokemons = it,
                    )
                )
            }

        }

    }
}


