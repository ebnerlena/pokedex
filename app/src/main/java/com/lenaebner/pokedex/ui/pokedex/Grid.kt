package com.lenaebner.pokedex.PokedexScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.paging.compose.items
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonsGrid(pokemons: LazyPagingItems<PokemonWithColor>) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(pokemons.itemCount) { index ->

            val listPokemon = pokemons.getAsState(index = index).value

            if(listPokemon != null) {
                FeaturedPokemon(
                    pokemon = listPokemon,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 6.dp)
                        .background(MaterialTheme.colors.background)
                        .animateContentSize()
                )
                Spacer(modifier = Modifier.size(4.dp))
            }

        }
        item {
            if (pokemons.loadState.append == LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(8.dp)
                )
            }
        }
    }
}