package com.lenaebner.pokedex.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.repository.pokemon.SearchPokemonPreview
import com.lenaebner.pokedex.ui.screenstates.SearchState
import com.lenaebner.pokedex.ui.theme.transparentGrey

@Composable
fun SearchPokemomResult(pokemon: SearchPokemonPreview) {
    val navController = ActiveNavController.current

    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("pokemon/${pokemon.id}?speciesId=${pokemon.speciesId}")
            }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "#${pokemon.id}   ${pokemon.name.capitalize()}",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
    }

    Divider(
        Modifier
            .height(2.dp)
            .padding(horizontal = 16.dp),
        color = transparentGrey
    )
}

@Composable
fun SearchResults(results: SearchState) {
    when(results) {
        is SearchState.Content ->  LazyColumn(
            Modifier
                .padding(8.dp)
                .animateContentSize()
        ) {
            items(results.pokemons) {
                SearchPokemomResult(it)
            }
        }
    }
}