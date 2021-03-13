package com.lenaebner.pokedex

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.data.Pokemon
import com.lenaebner.pokedex.ui.theme.PokedexTheme


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
                icon = R.drawable.arrow_left_long_grey)
                 },
        content = { PokemonsGrid(navController = navController) }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonsGrid(navController: NavController) {
    val pokemons = readData()
    val listState = rememberLazyListState()
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(pokemons) { p ->
            FeaturedPokemon(
                pokemon = p,
                navController = navController,
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun AllPokemons( navController: NavController) {
    val pokemons = readData()
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = Modifier.padding(16.dp)) {
        items(items = pokemons) { p ->
            FeaturedPokemon(
                pokemon = p,
                navController = navController,
                modifier = Modifier
                    .padding(16.dp)
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
                    .size(100.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.pokemon1),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .heightIn(min = 20.dp, max = 80.dp)
                        .padding(8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            val padding = Modifier.padding(horizontal = 16.dp)
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                modifier = padding
            )
            Text(
                text = pokemon.url,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary,
                modifier = padding
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}
