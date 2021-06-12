package com.lenaebner.pokedex.PokedexScreen


import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.LoadingSpinner
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.flow.collect


@Composable
fun Pokedex(pokemons: LazyPagingItems<PokemonWithColor>, backClicked: () -> Unit) {

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
            } else {
                PokemonsGrid(pokemons = pokemons)
            }
        }
    )
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun PokedexScreen(vm: PokedexViewModel) {

    val lazyPagingItems = vm.pokemons.collectAsLazyPagingItems()
    val navController = ActiveNavController.current

    Pokedex(pokemons = lazyPagingItems, backClicked = { navController.navigateUp() })

    LaunchedEffect(key1 = vm.actions) {
        vm.actions.collect {
            when(it) {
                is PokedexViewModel.PokedexScreenAction.NavigateBack -> navController.navigateUp()
                is PokedexViewModel.PokedexScreenAction.PokemonClicked -> navController.navigate(it.destination)
            }
        }
    }
}
