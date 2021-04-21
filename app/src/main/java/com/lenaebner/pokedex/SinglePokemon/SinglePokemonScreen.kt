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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.*
import com.lenaebner.pokedex.PokedexScreen.Type
import com.lenaebner.pokedex.ScreenStates.PokemonScreenState
import com.lenaebner.pokedex.api.models.EvolvingPokemons
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import com.lenaebner.pokedex.viewmodels.PokeScreenAction
import com.lenaebner.pokedex.viewmodels.PokemonViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow

@Preview
@Composable
fun PokemonScreenPreview() {
    SinglePokemonScreen(state= PokemonScreenState.Loading)
}


@Composable
fun SinglePokemonScreen(vm: PokemonViewModel) {

    val navController = ActiveNavController.current
    //val vm : PokemonViewModel = viewModel()
    val state = vm.uiState.observeAsState(initial = PokemonScreenState.Loading).value
    SinglePokemonScreen(state = state)

    LaunchedEffect("actions"){
        vm.actions.collect {
            when(it) {
                is PokeScreenAction.NavigateBack -> navController.navigateUp()
                is PokeScreenAction.pokemonClicked -> navController.navigate(it.destination)
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

@Composable
fun PokemonScreen(pokemon: Pokemon, species: PokemonSpecies?, evolutionChainEntries: List<EvolvingPokemons>, navigateBack: () -> Unit) {

    val color = if (species != null) species.color.name.asPokeColor() else transparentGrey
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
                            pokemon.types.forEach { type ->
                                Type(type = type)
                            }
                        }

                        Text(
                            text = if (species?.genera?.isNotEmpty() == true) species.genera[7].genus else "Type",
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
