package com.lenaebner.pokedex.shared

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
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
            composable("items") { ItemsScreen() }
            composable(
                "pokemon/{name}",
                arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                SinglePokemonScreen()
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
