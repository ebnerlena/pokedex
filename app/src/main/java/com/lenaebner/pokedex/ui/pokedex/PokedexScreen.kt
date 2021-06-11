package com.lenaebner.pokedex.PokedexScreen


import PokedexTheme
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import com.lenaebner.pokedex.ui.screenstates.PokedexScreenState
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel
import kotlinx.coroutines.flow.collect


@Preview
@Composable
fun PokedexPreview() {

    PokedexTheme {
        PokedexScreen(state = PokedexScreenState.Loading)
    }
}

@Composable
fun Pokedex(pokemons: List<PokemonWithColor>, backClicked: () -> Unit) {

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

            //PokemonsGrid(pokemons = pokemons)
        }
    )
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun PokedexScreen(vm: PokedexViewModel) {

    val lazyPagingItems = vm.pokemons.collectAsLazyPagingItems()

    if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    }
    PokemonsGrid(lazyPagingItems)

    //val uiState = vm.uiState.collectAsState().value
    //PokedexScreen(state = uiState)

    val navController = ActiveNavController.current

    LaunchedEffect(key1 = vm.actions) {
        vm.actions.collect {
            when(it) {
                is PokedexViewModel.PokedexScreenAction.NavigateBack -> navController.navigateUp()
                is PokedexViewModel.PokedexScreenAction.PokemonClicked -> navController.navigate(it.destination)
            }
        }
    }
}


@Composable
fun PokedexScreen(state: PokedexScreenState) {

    when(state) {
        is PokedexScreenState.Content -> Pokedex(
            pokemons = state.pokemonsWithColor,
            backClicked = state.backClicked
        )
        is PokedexScreenState.Loading -> loadingSpinner()
        is PokedexScreenState.Error -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            ErrorScreen(errorMessage = state.message, retry = state.retry)
        }
    }
}
