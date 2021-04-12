package com.lenaebner.pokedex.PokedexScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.ScreenStates.PokedexScreenState
import com.lenaebner.pokedex.api.models.PokemonWithColor
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.viewmodels.PokedexViewModel


@Preview
@Composable
fun PokedexPreview() {

    PokedexTheme {
        PokedexScreen(navController = rememberNavController())
    }
}

@Composable
fun Pokedex (pokemons: List<PokemonWithColor>, navController: NavController) {

    Scaffold (
        topBar = {
            Header(
                navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Pokedex",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack
            )
        },
        content = {

            PokemonsGrid(navController = navController, pokemons = pokemons)
        }
    )
}

@Composable
fun PokedexScreen(navController: NavController) {

    val vm: PokedexViewModel = viewModel()
    
    val uiState = vm.uiState.observeAsState(initial = PokedexScreenState.Loading).value
    PokedexScreen(state = uiState, navController = navController)
}

@Composable
fun PokedexScreen(state: PokedexScreenState, navController: NavController) {

    when(state) {
        is PokedexScreenState.Content -> Pokedex(
            pokemons = state.pokemonsWithColor,
            navController = navController
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
