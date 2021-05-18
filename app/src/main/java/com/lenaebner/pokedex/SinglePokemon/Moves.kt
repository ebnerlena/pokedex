package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.Pokemon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Moves(pokemon: Pokemon) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(pokemon.moves) { m ->
            Text(text = m.move.name.toUpperCase(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .background(MaterialTheme.colors.background)
                ,textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}