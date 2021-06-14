package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.ui.theme.transparentGrey
import java.util.*

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

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(vertical = 8.dp)
        ) {
            pokemon.abilities?.forEach {

                Text(
                    text = it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}