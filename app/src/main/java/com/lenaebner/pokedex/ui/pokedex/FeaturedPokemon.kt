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
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.lenaebner.pokedex.asPokeColor
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite


@Composable
fun FeaturedPokemon(
    pokemon: PokemonWithColor,
    modifier: Modifier = Modifier,
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = pokemon.color.asPokeColor()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = pokemon.onClick)
        ) {
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = pokemon.name.capitalize(),
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    modifier = Modifier
                        .weight(3f)
                        .padding(top = 6.dp, start = 12.dp)
                )
                Text(
                    text = '#'+pokemon.id.toString(),
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
                    pokemon.types?.forEach { type ->
                        Type(type = type)
                    }
                }
                Column(modifier = Modifier
                    .weight(3f)
                    .padding(2.dp)) {

                    val painter = rememberCoilPainter(
                        request = pokemon.sprite
                    )

                    Image(
                        modifier = Modifier
                            .heightIn(min = 20.dp, max = 80.dp)
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                            .background(transparentWhite),
                        painter = painter,
                        contentDescription = "Pokemon Image",
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    )
                }
            }
        }
    }
}