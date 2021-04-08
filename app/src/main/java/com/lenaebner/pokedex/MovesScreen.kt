package com.lenaebner.pokedex

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.lenaebner.pokedex.PokedexScreen.PokemonsGrid
import com.lenaebner.pokedex.shared.Header

@Composable
fun Moves (navController: NavController) {

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Moves",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { PokemonsGrid(navController = navController, pokemons = emptyList()) }
    )
}