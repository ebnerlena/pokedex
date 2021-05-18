package com.lenaebner.pokedex.ui.screenstates

import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor

sealed class PokedexScreenState {
    object Loading: PokedexScreenState()
    data class Content(
        val pokemonsWithColor: List<PokemonWithColor>,
        val backClicked: () -> Unit
    ) : PokedexScreenState()
    data class Error(val message: String, val retry: () -> Unit): PokedexScreenState()
}