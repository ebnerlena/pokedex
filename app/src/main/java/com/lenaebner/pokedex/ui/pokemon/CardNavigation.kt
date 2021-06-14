package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.lenaebner.pokedex.repository.pokemon.EvolvingPokemons
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.repository.pokemon.Species
import com.lenaebner.pokedex.ui.theme.transparentGrey
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CardNavigation(
    pokemon: Pokemon,
    species: Species?,
    evolutionChainEntries: List<EvolvingPokemons>?,
) {
    Card(modifier = Modifier
        .padding(top = 0.dp)
        .offset(y = 10.dp)
        .fillMaxSize(),
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp
    ) {

        val pages = listOf("About", "Stats", "Evolution")
        val pagerState = rememberPagerState(pageCount = pages.size)
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.padding(bottom = 16.dp)) {

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                },
                backgroundColor = Color.White
            ) {
                // Add tabs for all of our pages
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title) },
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = transparentGrey
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when(page) {
                    0 -> Description(pokemon = pokemon, species = species)
                    1 -> Stats(pokemon)
                    2 -> EvolutionChain(evolutionChainEntries?: emptyList())
                }
            }

        }

    }
}