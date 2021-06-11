package com.lenaebner.pokedex

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import com.lenaebner.pokedex.HomeScreen.Home
import com.lenaebner.pokedex.ItemsScreen.ItemsScreen
import com.lenaebner.pokedex.PokedexScreen.PokedexScreen
import com.lenaebner.pokedex.SinglePokemon.SinglePokemonScreen
import com.lenaebner.pokedex.ui.viewmodels.PokemonViewModel
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel
import com.lenaebner.pokedex.ui.viewmodels.SearchViewModel
import com.lenaebner.pokedex.viewmodels.ItemViewModel
import com.lenaebner.pokedex.viewmodels.ItemsViewModel


@OptIn(ExperimentalPagingApi::class)
@Composable
fun Navigation() {

    val navController = rememberNavController()

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
                val vm: PokedexViewModel = hiltViewModel(backStackEntry = it)
                PokedexScreen(vm = vm)
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
            composable("items") {
                val vm: ItemsViewModel = hiltViewModel(it)
               ItemsScreen(vm = vm)
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
            }        }
    }
}
