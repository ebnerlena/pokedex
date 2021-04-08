package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import com.lenaebner.pokedex.R
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.*
import com.lenaebner.pokedex.PokedexScreen.Type
import com.lenaebner.pokedex.api.models.EvolutionChainDetails
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.ui.theme.transparentWhite
import com.lenaebner.pokedex.viewmodels.PokeViewModel
import com.lenaebner.pokedex.viewmodels.PokemonScreenState

@Preview
@Composable
fun SinglePokemonScreenPreview() {
    val navC = rememberNavController()
    SinglePokemonScreen(pokemonName = "pikachu", navController = navC)
}

@Composable
fun SinglePokemonScreen(pokemonName: String?, navController: NavController) {

    val vm : PokeViewModel = PokeViewModel(pokemonName ?: "pikachu")
    val state = vm.state.observeAsState().value
    SinglePokemonScreen(navController = navController, state = state)
    
}

@Composable
fun SinglePokemonScreen(navController: NavController, state: PokemonScreenState?) {

    when (state) {
        is PokemonScreenState.content -> PokemonScreen(
            pokemon = state.pokemon,
            species = state.species,
            evolutionChain = state.evolutionChain,
            navController = navController
        )
        PokemonScreenState.loading -> loadingSpinner()
    }
}
@Composable
fun PokemonScreen(navController: NavController, pokemon: Pokemon, species: PokemonSpecies, evolutionChain: EvolutionChainDetails) {

    PokedexTheme {

        Scaffold (
            topBar = {
                Header(navController = navController,
                    textColor = Color.White,
                    backgroundColor = species.color.name.asPokeColor(),
                    title = pokemon.name.capitalize(),
                    icon = Icons.Default.ArrowBack,
                    pokemon = pokemon
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(species.color.name.asPokeColor())) {
                    Row(modifier = Modifier
                        .padding(top=8.dp, start=16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            pokemon.types.forEach { type ->
                                Type(type = type)
                            }
                        }

                        Text(
                            text = if (species.genera.isNotEmpty()) species.genera[7].genus else "Type",
                            color = Color.White,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .weight(1f),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CoilImage(
                            data = pokemon.sprites?.other?.artwork?.sprite.orEmpty(),
                            contentDescription = "Pikachu",
                            loading = {
                                Image(
                                    painter = painterResource(id = R.drawable.pokemon1),
                                    contentDescription = "Fallback Image"
                                )
                            },
                            requestBuilder = {
                                transformations(CircleCropTransformation())
                            },modifier = Modifier
                                .fillMaxHeight()
                                .width(120.dp)
                                .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                                .background(transparentWhite)
                        )
                    }
                    Row(modifier = Modifier.weight(3f)){
                        CardNavigation("about", pokemon = pokemon, species = species, evolutionChainDetails = evolutionChain, navController = navController)
                    }
                }
            }
        )
    }
}