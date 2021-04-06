package com.lenaebner.pokedex

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.*
import com.lenaebner.pokedex.ui.theme.*
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonsGrid(navController: NavController, pokemons: List<Pokemon>?) {

    var pokemonsWithColors: MutableState<List<PokemonWithColor>> = mutableStateOf(
        emptyList()
    )

   pokemonsWithColors = fetchPokemons(offset = 0, limit = 30, pokemons = pokemons)

    //pokemonsWithColors.value.addAll(list2.value)

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(pokemonsWithColors.value) { p ->
            FeaturedPokemon(
                pokemon = p,
                navController = navController,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun FeaturedPokemon(
    pokemon: PokemonWithColor,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = pokemon.color.color.name.asPokeColor()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("pokemon/${pokemon.pokemon?.name}") {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
        ) {
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = pokemon.pokemon?.name.toString().capitalize(),
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    modifier = Modifier
                        .weight(3f)
                        .padding(top = 6.dp, start = 12.dp)
                )
                Text(
                    text = '#'+pokemon.pokemon?.id.toString(),
                    style = MaterialTheme.typography.body2,
                    color = transparentGrey,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 6.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.End)
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp, end = 4.dp, top = 16.dp)
                    .align(
                        Alignment.Top
                    )) {
                    pokemon.pokemon?.types?.forEach { type ->
                        Type(type = type)
                    }
                }
                Column(modifier = Modifier
                    .weight(3f)
                    .padding(2.dp)) {
                    CoilImage(
                        data = pokemon.pokemon?.sprites?.other?.artwork?.sprite.orEmpty(),
                        contentDescription = "Pikachu",
                        loading = {
                            Image(
                                painter = painterResource(id = R.drawable.pokemon1),
                                contentDescription = "Fallback Image"
                            )
                        },
                        requestBuilder = {
                            transformations(CircleCropTransformation())
                        },
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .heightIn(min = 20.dp, max = 80.dp)
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                            .background(transparentWhite)
                    )
                }
            }
        }
    }
}

@Composable
fun Type(type: Type) {
    Card(shape= MaterialTheme.shapes.medium, modifier = Modifier
        .padding(1.dp),
        backgroundColor= transparentWhite) {
        Text(text = type.type.name.capitalize(), color = White, modifier = Modifier
            .padding(vertical = 3.dp, horizontal = 6.dp), style = MaterialTheme.typography.caption,)
    }
}
