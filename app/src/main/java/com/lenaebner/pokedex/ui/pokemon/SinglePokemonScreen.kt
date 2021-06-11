package com.lenaebner.pokedex.SinglePokemon

import PokedexTheme
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.coil.rememberCoilPainter
import com.lenaebner.pokedex.*
import com.lenaebner.pokedex.PokedexScreen.Type
import com.lenaebner.pokedex.repository.pokemon.EvolvingPokemons
import com.lenaebner.pokedex.repository.pokemon.Pokemon
import com.lenaebner.pokedex.repository.pokemon.Species
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.ui.screenstates.PokemonScreenState
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import com.lenaebner.pokedex.ui.viewmodels.PokemonScreenAction
import com.lenaebner.pokedex.ui.viewmodels.PokemonViewModel
import kotlinx.coroutines.flow.collect

@Preview
@Composable
fun PokemonScreenPreview() {
    SinglePokemonScreen(state= PokemonScreenState.Loading)
}


@Composable
fun SinglePokemonScreen(vm: PokemonViewModel) {

    val navController = ActiveNavController.current
    val state = vm.uiState.collectAsState(initial = PokemonScreenState.Loading).value
    SinglePokemonScreen(state = state)

    LaunchedEffect(vm.actions){
        vm.actions.collect {
            when(it) {
                is PokemonScreenAction.NavigateBack -> navController.navigateUp()
                is PokemonScreenAction.PokemonClicked -> navController.navigate(it.destination)
            }
        }
    }
}

@Composable
fun SinglePokemonScreen(state: PokemonScreenState) {

    when (state) {
        is PokemonScreenState.Content -> PokemonScreen(
            pokemon = state.pokemon,
            species = state.species,
            evolutionChainEntries = state.evolutionChainPokemons,
            navigateBack = state.backClicked
        )
        is PokemonScreenState.Loading -> loadingSpinner()
        is PokemonScreenState.Error -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            ErrorScreen(errorMessage = state.message, retry = state.retry)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokemonScreen(pokemon: Pokemon, species: Species?, evolutionChainEntries: List<EvolvingPokemons>, navigateBack: () -> Unit) {

    val color = if (species != null) species.color.asPokeColor() else transparentGrey
    PokedexTheme {

        Scaffold (
            topBar = {
                Header(
                    textColor = Color.White,
                    backgroundColor = color,
                    title = pokemon.name.capitalize(),
                    icon = Icons.Default.ArrowBack,
                    pokemon = pokemon,
                    backClicked = navigateBack,
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(color)) {
                    Row(modifier = Modifier
                        .padding(top=8.dp, start=16.dp)) {
                        Row(modifier = Modifier.weight(1f)) {
                            pokemon.types?.forEach { type ->
                                Type(type = type)
                            }
                        }

                        Text(
                            text = if (species?.genera?.isNotEmpty() == true) species.genera else "Type",
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
                        val painter = rememberCoilPainter(
                            request = pokemon.sprite
                        )

                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .animateContentSize()
                                .width(120.dp)
                                .padding(horizontal = 0.dp, vertical = 8.dp)
                                .background(transparentWhite),
                            painter = painter,
                            contentDescription = "Pokemon Image",
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.Center
                        )
                    }
                    Row(modifier = Modifier.weight(3f)){
                        CardNavigation(
                            page = "about",
                            pokemon = pokemon,
                            species = species,
                            evolutionChainEntries = evolutionChainEntries
                        )
                    }
                }
            }
        )
    }
}
