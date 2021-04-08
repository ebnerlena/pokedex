package com.lenaebner.pokedex.PokedexScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor
import com.lenaebner.pokedex.api.models.Type
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.ui.theme.transparentWhite
import kotlinx.coroutines.*


@Preview
@Composable
fun PokedexPreview() {

    PokedexTheme {
        Pokedex(navController = rememberNavController())
    }
}

@Composable
fun Pokedex (navController: NavController) {

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

            PokemonsGrid(navController = navController, pokemons = emptyList())
        }
    )
}

@Composable
fun fetchPokemons(offset: Int, limit: Int, pokemons: List<Pokemon>?) : MutableState<List<PokemonWithColor>> {
    val scope = rememberCoroutineScope()
    val list: MutableState<List<Pokemon>> = mutableStateOf(emptyList())
    val pokemonsWithColors: MutableState<List<PokemonWithColor>> = mutableStateOf(emptyList())

    list.value = pokemons ?: emptyList()

    val task = scope.launch {
        val pokemons = withContext(Dispatchers.IO) {
            ApiController.pokeApi.getPokemons(offset = offset,limit = limit)
        }

        if (list.value.isEmpty()) {
            list.value = pokemons.results.map {
                async { ApiController.pokeApi.getPokemon(it.name) }
            }.awaitAll()
        }

        withContext(Dispatchers.IO){
            pokemonsWithColors.value = list.value.map {
                PokemonWithColor(
                    it,
                    ApiController.pokeApi.getPokemonColor(it.id)
                )
            }
        }
    }
    return pokemonsWithColors
}
