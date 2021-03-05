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
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.data.Pokemon
import com.lenaebner.pokedex.ui.theme.PokedexTheme


@Composable
fun Pokedex () {
    val navController = rememberNavController()
    Scaffold (
        topBar = {
            TopAppBar() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.navigate("home") {
                            launchSingleTop = true
                            popUpTo = navController.graph.startDestination
                        } }) {
                        Image(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null,
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .padding(8.dp)
                        )
                    }

                    Text(
                        text = "Pokedex",
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
                 },
        content = { AllPokemons() }
    )
}

@Composable
fun AllPokemons() {
    val pokemons = readData()
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = Modifier.padding(16.dp)) {
        items(items = pokemons) { p ->
            FeaturedPokemon(
                pokemon = p,
                modifier = Modifier.padding(16.dp).background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun FeaturedPokemon(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

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

@Preview
@Composable
fun PokedexPreview() {
    PokedexTheme {
        Pokedex()
    }
}