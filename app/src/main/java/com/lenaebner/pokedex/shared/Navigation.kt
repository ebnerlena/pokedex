package com.lenaebner.pokedex.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
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

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { Home(navController = navController) }
        composable("pokedex") { PokedexScreen(navController = navController) }
        composable("generations") { Generations(navController = navController) }
        composable("moves") { Moves(navController = navController) }
        composable("items") { ItemsScreen(navController = navController) }
        composable(
            "pokemon/{pokemonName}",
            arguments = mutableStateListOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            SinglePokemonScreen(
                pokemonName = backStackEntry.arguments?.getString("pokemonName"),
                navController = navController
            )
        }
        composable(
            "generation/{name}",
            arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            SingleGeneration(
                name = backStackEntry.arguments?.getString("name"),
                navController = navController
            )
        }
        composable(
            "item/{name}",
            arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            SingleItem(
                itemName = backStackEntry.arguments?.getString("name"),
                navController = navController
            )
        }
    }

    val ActiveNavController = compositionLocalOf<NavController> {error("No navcontroller found") }
    //TODO: fix to working
    /*CompositionLocalProvider(ActiveNavController provides navController) {
        Home()
    } */

}