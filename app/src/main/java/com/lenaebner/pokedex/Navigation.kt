package com.lenaebner.pokedex

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltNavGraphViewModel

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.HomeScreen.Home
import com.lenaebner.pokedex.PokedexScreen.PokedexScreen
import com.lenaebner.pokedex.SinglePokemon.SinglePokemonScreen
import com.lenaebner.pokedex.ui.viewmodels.PokemonViewModel
import com.lenaebner.pokedex.ui.viewmodels.PokedexViewModel


@Composable
fun Navigation() {

    val navController = rememberNavController()

    CompositionLocalProvider(ActiveNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") { Home() }
            composable("pokedex") {
                val vm: PokedexViewModel = hiltNavGraphViewModel(it)
                PokedexScreen(vm = vm)
            }
            composable(
                "pokemon/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                })
            ) { backStackEntry ->
                //val name = backStackEntry.arguments?.getString("name") ?: "pikachu"
                val vm: PokemonViewModel = hiltNavGraphViewModel(backStackEntry)
                SinglePokemonScreen(vm = vm)
            }
        }
    }
}
