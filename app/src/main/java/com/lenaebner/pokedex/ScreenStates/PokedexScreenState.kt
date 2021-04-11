package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.PokemonWithColor

sealed class PokedexScreenState {
    object Loading: PokedexScreenState()
    data class Content(
        val pokemonsWithColor: MutableList<PokemonWithColor>
        ) : PokedexScreenState()
    data class Error(val message: String, val retry: () -> Unit): PokedexScreenState()
}