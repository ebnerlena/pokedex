package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.Pokemon
import com.lenaebner.pokedex.ui.theme.transparentGrey


@Composable
fun HeightWidth(pokemon: Pokemon) {
    Card( modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), elevation = 2.dp, backgroundColor = Color.White, shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                Text(text = "Height",
                    color = transparentGrey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start=32.dp, bottom = 8.dp)
                )

                Text(text = (pokemon.height/10.0).toString() + " cm",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start=32.dp)
                )

            }
            Column(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                Text(text = "Weight",
                    color = transparentGrey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(start=32.dp, bottom = 8.dp)
                )

                Text(text = (pokemon.weight/10.0).toString()+" kg",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start=32.dp)
                )

            }
        }
    }
}