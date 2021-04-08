package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.api.models.Pokemon
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    private fun fetch(name: String) {
        viewModelScope.launch {
            _pokemon.postValue(ApiController.pokeApi.getPokemon(name))
        }
    }

}