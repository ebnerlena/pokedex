package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.R
import com.lenaebner.pokedex.api.models.EvolutionChainDetails
import com.lenaebner.pokedex.api.models.EvolveEntry
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.ui.theme.transparentGrey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun EvolutionChain(evolutionChainDetails: EvolutionChainDetails, navController: NavController) {
    var evolves = evolutionChainDetails.chain.evolves_to
    var species = evolutionChainDetails.chain.species

    LazyColumn {
        item {
            Text(text = "Evolution Chain",
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 16.dp)
            )
        }
        item {
            while(evolves.isNotEmpty()) {
                EvolutionEntry(evolveEntry = evolves[0], speciesName = species?.name.toString(), navController = navController)
                species = evolves[0].species
                evolves = evolves[0].evolves_to

                if(evolves.isNotEmpty()) {
                    Divider(modifier = Modifier
                        .height(2.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(), color = transparentGrey
                    )
                }
            }
        }
    }
}

@Composable
fun EvolutionEntry(evolveEntry: EvolveEntry, speciesName: String, navController: NavController) {

    val details = evolveEntry.evolution_details[0]
    val triggerText = when(details.trigger.name) {
        "level-up" -> if(details.min_happiness != null) "Hpy: "+details.min_happiness else "Lvl: "+evolveEntry.evolution_details[0].min_level
        "use-item" -> details.item?.name?.capitalize()
        else -> "Level up"
    }
    var pokeFrom  by remember { mutableStateOf(Pokemon()) }
    var pokeTo by remember { mutableStateOf(Pokemon()) }

    val scope = rememberCoroutineScope()
    scope.launch {
        pokeFrom = withContext(Dispatchers.IO) {
            ApiController.pokeApi.getPokemon(speciesName)
        }
        pokeTo = withContext(Dispatchers.IO) {
            ApiController.pokeApi.getPokemon(evolveEntry.species.name)
        }
    }

    Row(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier
            .weight(1f)
            .clickable {
                navController.navigate("pokemon/${speciesName}")
            }) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                CoilImage(
                    data = pokeFrom.sprites?.other?.artwork?.sprite.orEmpty(),
                    contentDescription = "Pokemon From",
                    loading = {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon1),
                            contentDescription = "Fallback Image"
                        )
                    },
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    },modifier = Modifier
                        .width(60.dp)
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = speciesName.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }
        Column(modifier = Modifier
            .padding(8.dp)
            .weight(1f)
            .fillMaxHeight()
            .align(Alignment.CenterVertically)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = transparentGrey
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                Text(text = triggerText.toString(),
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
        Column(modifier = Modifier
            .weight(1f)
            .clickable { navController.navigate("pokemon/" + evolveEntry.species.name) }
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                CoilImage(
                    data = pokeTo.sprites?.other?.artwork?.sprite.orEmpty(),
                    contentDescription = "Pokemon From",
                    loading = {
                        Image(
                            painter = painterResource(id = R.drawable.pokemon1),
                            contentDescription = "Fallback Image"
                        )
                    },
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    },modifier = Modifier
                        .width(60.dp)
                )
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = evolveEntry.species.name.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }
    }
}
