package com.lenaebner.pokedex

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.lenaebner.pokedex.HomeScreen.Home
import com.lenaebner.pokedex.ItemsScreen.ItemsScreen
import com.lenaebner.pokedex.PokedexScreen.PokedexScreen
import com.lenaebner.pokedex.SinglePokemon.SinglePokemonScreen
import com.lenaebner.pokedex.ui.viewmodels.PokemonViewModel
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel
import com.lenaebner.pokedex.ui.viewmodels.SearchViewModel
import com.lenaebner.pokedex.viewmodels.ItemViewModel
import com.lenaebner.pokedex.viewmodels.ItemsViewModel
import kotlinx.coroutines.flow.collect


@OptIn(ExperimentalPagingApi::class)
@Composable
fun Navigation() {

    // workaround for keeping grid position when navigating
    val lazyItemsListState = rememberLazyListState()
    val lazyPokemonsListState = rememberLazyListState()

    val itemsVM: ItemsViewModel = hiltViewModel()
    val items = itemsVM.items.collectAsLazyPagingItems()

    val pokedexVM: PokedexViewModel = hiltViewModel()
    val pokemons = pokedexVM.pokemons.collectAsLazyPagingItems()

    val navController = rememberNavController()

    LaunchedEffect(key1 = itemsVM.actions) {
        itemsVM.actions.collect {
            when(it) {
                is ItemsViewModel.ItemsScreenAction.Navigate -> navController.navigate(it.destination)
                is ItemsViewModel.ItemsScreenAction.NavigateBack -> navController.navigateUp()
            }
        }
    }

    LaunchedEffect(key1 = pokedexVM.actions) {
        pokedexVM.actions.collect {
            when(it) {
                is PokedexViewModel.PokedexScreenAction.NavigateBack -> navController.navigateUp()
                is PokedexViewModel.PokedexScreenAction.PokemonClicked -> navController.navigate(it.destination)
            }
        }
    }

    CompositionLocalProvider(ActiveNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                val vm: SearchViewModel = hiltViewModel(it)
                Home(vm = vm)
            }
            composable("pokedex") {
                PokedexScreen(lazyListState = lazyPokemonsListState, lazyPagingItems = pokemons)
            }
            composable(
                "pokemon/{pokemonId}?speciesId={speciesId}",
                arguments = listOf(navArgument("pokemonId") {
                    type = NavType.IntType
                    nullable = false
                },
                navArgument("speciesId") {
                    type = NavType.IntType
                    nullable = false
                },
                )
            ) { backStackEntry ->
                val vm: PokemonViewModel = hiltViewModel(backStackEntry)
                SinglePokemonScreen(vm = vm)
            }
            composable("items") { backStackEntry ->
               ItemsScreen(lazyListState = lazyItemsListState, lazyPagingItems = items)
            }
            composable(
                "item/{itemId}",
                arguments = listOf(navArgument("itemId") {
                    type = NavType.IntType
                    nullable = false
                } )
            ) { backStackEntry ->
                val vm: ItemViewModel = hiltViewModel(backStackEntry)
                ItemScreen(vm = vm)
            }
        }
    }
}
