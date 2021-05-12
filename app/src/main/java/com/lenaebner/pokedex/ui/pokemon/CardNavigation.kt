package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.PokemonSpecies
import com.lenaebner.pokedex.repository.EvolvingPokemons
import com.lenaebner.pokedex.repository.Pokemon
import com.lenaebner.pokedex.repository.Species


@Composable
fun CardNavigation(
    page: String,
    pokemon: Pokemon,
    species: Species?,
    evolutionChainEntries: List<EvolvingPokemons>,
) {
    Card(modifier = Modifier
        .padding(top = 0.dp)
        .offset(y = 10.dp)
        .fillMaxSize(),
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp
    ) {

        var currentPage by rememberSaveable { mutableStateOf(page) }

        Column(modifier = Modifier.padding(bottom = 16.dp)) {

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .weight(1f)) {
                TextButton(onClick = { currentPage = "about" }) {
                    val textColor =  if (currentPage == "about") Color.Black else MaterialTheme.colors.secondaryVariant
                    Text(text = "About", color = textColor)
                }
                TextButton(onClick = { currentPage = "stats" }) {
                    val textColor =  if (currentPage == "stats") Color.Black else MaterialTheme.colors.secondaryVariant
                    Text(text = "Base Stats", color = textColor)
                }
                TextButton(onClick = { currentPage = "evolution" }) {
                    val textColor =  if (currentPage == "evolution") Color.Black else MaterialTheme.colors.secondaryVariant
                    Text(text = "Evolution", color = textColor)
                }
                TextButton(onClick = { currentPage = "moves" }) {
                    val textColor =  if (currentPage == "moves") Color.Black else MaterialTheme.colors.secondaryVariant
                    Text(text = "Moves", color = textColor)
                }
            }

            Divider(modifier = Modifier
                .height(3.dp)
                .padding(3.dp)
                .background(MaterialTheme.colors.secondaryVariant),
                color = MaterialTheme.colors.secondaryVariant
            )

            Row(Modifier.weight(5f)) {
                Crossfade(targetState = currentPage) { screen ->
                    when (screen) {
                        "about" -> Description(pokemon = pokemon, species = species)
                        "stats" -> Stats(pokemon)
                        "moves" -> Moves(pokemon)
                        "evolution" -> EvolutionChain(evolutionChainEntries)
                    }
                }
            }
        }

    }
}