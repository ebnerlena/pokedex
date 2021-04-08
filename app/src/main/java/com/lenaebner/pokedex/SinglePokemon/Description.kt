package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies


@Composable
fun Description(pokemon: Pokemon, species: PokemonSpecies) {
    val text = if (species.flavor_text_entries.isNotEmpty()) {
        species.flavor_text_entries[7].flavor_text.replace("[\n\r]".toRegex(), " ") }
    else "loading..."
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            Text(text = text,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
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


