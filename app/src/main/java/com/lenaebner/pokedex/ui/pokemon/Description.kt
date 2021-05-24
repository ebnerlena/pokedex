package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.repository.pokemon.Species


@Composable
fun Description(pokemon: Pokemon, species: Species?) {
    val text = species?.flavor_text_entry ?: "loading..."

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            Text(
                text = text,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp).animateContentSize()
            )
        }

        item {
            HeightWidth(pokemon = pokemon)
        }
        item {
            EggGroups(species = species)
        }

        item {
            Abilities(pokemon)
        }
    }
}


