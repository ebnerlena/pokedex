package com.lenaebner.pokedex.PokedexScreen


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.LoadingSpinner
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.flow.collect


@Composable
fun Pokedex(pokemons: LazyPagingItems<PokemonWithColor>, backClicked: () -> Unit, lazyListState: LazyListState) {

    Scaffold (
        topBar = {
            Header(
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = MaterialTheme.colors.background,
                title = "Pokedex",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack,
                backClicked = backClicked
            )
        },
        content = {

            if (pokemons.loadState.refresh == LoadState.Loading) {
                LoadingSpinner()
            }
            else {
                PokemonsGrid(pokemons = pokemons, lazyListState)
            }
        }
    )
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun PokedexScreen(lazyListState: LazyListState, lazyPagingItems: LazyPagingItems<PokemonWithColor>) {

    //val lazyPagingItems = vm.pokemons.collectAsLazyPagingItems()
    val navController = ActiveNavController.current

    Pokedex(pokemons = lazyPagingItems, backClicked = { navController.navigateUp() }, lazyListState)
}
