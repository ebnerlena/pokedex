package com.lenaebner.pokedex.PokedexScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonsGrid(navController: NavController, pokemons: List<Pokemon>?) {

    var pokemonsWithColors: MutableState<List<PokemonWithColor>> = mutableStateOf(
        emptyList()
    )

    pokemonsWithColors = fetchPokemons(offset = 0, limit = 30, pokemons = pokemons)

    //pokemonsWithColors.value.addAll(list2.value)

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(pokemonsWithColors.value) { p ->
            FeaturedPokemon(
                pokemon = p,
                navController = navController,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}