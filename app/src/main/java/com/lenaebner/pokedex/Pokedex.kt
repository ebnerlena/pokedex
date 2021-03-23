package com.lenaebner.pokedex

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.Debug
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonList
import com.lenaebner.pokedex.api.models.PokemonPreview
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Pokedex",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
                 },
        content = { PokemonsGrid(navController = navController) }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonsGrid(navController: NavController) {

    val scope = rememberCoroutineScope()
    var list: MutableState<List<Pokemon>> = mutableStateOf(emptyList())
    val task = scope.launch {
        val pokemons = withContext(Dispatchers.IO){
            ApiController.pokeApi.getPokemons()
        }
        Log.d("foo", pokemons.results.size.toString())
        list.value = pokemons.results.map {
            ApiController.pokeApi.getPokemon(it.name)
        }
    }

    val listState = rememberLazyListState()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(list.value) { p ->
            FeaturedPokemon(
                pokemon = p,
                navController = navController,
                modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun FeaturedPokemon(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("pokemon/${pokemon.name}") {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .size(90.dp).align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CoilImage(
                    data = pokemon.sprites?.other?.artwork?.sprite.orEmpty(),
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
                        .padding(8.dp))
            }

            Spacer(Modifier.height(8.dp))

            val modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterHorizontally)
            Text(
                text = pokemon.name.capitalize(),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = modifier,
            )
        }
    }
}
