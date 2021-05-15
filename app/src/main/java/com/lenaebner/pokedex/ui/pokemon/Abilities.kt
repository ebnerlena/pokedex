package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.ui.theme.transparentGrey

@Composable
fun Abilities(pokemon: Pokemon) {
    Row {
        Text(
            text = "Abilities:",
            color = transparentGrey,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .weight(1.2f)
        )

        Row(modifier = Modifier.weight(2f)) {
            pokemon.abilities?.forEach {
                Text(
                    text = it.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                )
            }
        }
    }
}