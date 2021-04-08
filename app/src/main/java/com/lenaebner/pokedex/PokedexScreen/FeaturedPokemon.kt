package com.lenaebner.pokedex.PokedexScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.lenaebner.pokedex.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.PokemonWithColor
import com.lenaebner.pokedex.asPokeColor
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite


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