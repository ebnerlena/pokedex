package com.lenaebner.pokedex

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.Debug
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.*
import com.lenaebner.pokedex.ui.theme.*
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview
@Composable
fun GenerationPreview() {

    PokedexTheme {
        SingleGeneration(name = "generation-iv", navController = rememberNavController())
    }
}

@Composable
fun SingleGeneration(name: String?, navController: NavController) {

    val scope = rememberCoroutineScope()
    var generation by remember { mutableStateOf(Generation())}
    var pokemonList: MutableState<List<PokemonSpecies>> = mutableStateOf(emptyList())

    scope.launch {
        generation = withContext(Dispatchers.IO){
            ApiController.pokeApi.getGeneration(name ?: "generation-iv")
        }

        withContext(Dispatchers.IO) {
            pokemonList.value = generation.pokemon_species.map {
                val id = it.url.split("/").get(6).toInt()
                ApiController.pokeApi.getPokemonSpecies(id)
            }
        }
    }

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = name?.toUpperCase() ?: "Generation XY",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = {

            Species(navController = navController, pokemons = pokemonList.value)
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Species(navController: NavController, pokemons: List<PokemonSpecies>) {

    val scope = rememberCoroutineScope()
    var species: MutableState<List<PokemonSpecies>> = mutableStateOf(pokemons)
    Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
        Text(
            text = "Species",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 6.dp)
        )

        LazyVerticalGrid(
            cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
        ) {
            items(species.value) { specie ->
                Text(
                    text = specie.name.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 6.dp)
                        .background(MaterialTheme.colors.surface)

                )
                Spacer(modifier = Modifier.size(4.dp))
            }
        }
    }

}


