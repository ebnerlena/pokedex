package com.lenaebner.pokedex.ui.screenstates

import com.lenaebner.pokedex.repository.pokemon.SearchPokemonPreview

sealed class SearchState {
    object Loading: SearchState()
    data class Content(
        val pokemons: List<SearchPokemonPreview>,
    ) : SearchState()
    data class Error(val message: String, val retry: () -> Unit): SearchState()
}
