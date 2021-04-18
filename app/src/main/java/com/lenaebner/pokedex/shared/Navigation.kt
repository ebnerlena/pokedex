package com.lenaebner.pokedex.shared

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.*
import com.lenaebner.pokedex.HomeScreen.Home
import com.lenaebner.pokedex.ItemsScreen.Items
import com.lenaebner.pokedex.ItemsScreen.ItemsScreen
import com.lenaebner.pokedex.PokedexScreen.PokedexScreen
import com.lenaebner.pokedex.SinglePokemon.SinglePokemonScreen
import com.lenaebner.pokedex.viewmodels.ItemsViewModel
import com.lenaebner.pokedex.viewmodels.PokemonViewModel
import com.lenaebner.pokedex.viewmodels.PokemonViewModelFactory

@ExperimentalMaterialApi
@Composable
fun Navigation() {

    val navController = rememberNavController()

    CompositionLocalProvider(ActiveNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") { Home() }
            composable("pokedex") { PokedexScreen() }
            composable("moves") { Moves() }
            composable("items") {
                val vm: ItemsViewModel = viewModel(key="items")
                ItemsScreen(vm = vm)
            }
            composable(
                "pokemon/{name}",
                arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "pikachu"
                val vm: PokemonViewModel = viewModel(factory = PokemonViewModelFactory(name))
                SinglePokemonScreen(vm=vm)
                //SinglePokemonScreen()
            }
            composable(
                "item/{name}",
                arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                ItemScreen()
            }
        }
    }


}
