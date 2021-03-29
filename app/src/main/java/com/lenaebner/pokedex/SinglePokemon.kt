package com.lenaebner.pokedex

import android.service.autofill.OnClickAction
import android.util.Log
import android.widget.Space
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.*
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview
@Composable
fun PokemonPreview() {
    val navC = rememberNavController()
    SinglePokemonScreen(pokemonName = "pikachu", navController = navC)
}

@Composable
fun SinglePokemonScreen(pokemonName: String?, navController: NavController) {

    val scope = rememberCoroutineScope()
    var pokemon by remember { mutableStateOf(Pokemon())}
    var species by remember { mutableStateOf(PokemonSpecies())}
    var evolutionChain by remember { mutableStateOf(EvolutionChainDetails(Chain()))}

    scope.launch {
        pokemon = withContext(Dispatchers.IO){
             ApiController.pokeApi.getPokemon(pokemonName ?: "pikachu")
        }
        species = withContext(Dispatchers.IO){
            ApiController.pokeApi.getPokemonSpecies(pokemon.id)
        }
        evolutionChain = withContext(Dispatchers.IO){
            val id = species.evolution_chain.url.split("/").get(6).toInt()
            Log.d("foo", " id: "+id.toString())
            ApiController.pokeApi.getEvolutionChain(id)
        }
    }

    PokedexTheme() {

        Scaffold (
            topBar = {
                Header(navController = navController,
                    textColor = White,
                    backgroundColor = ConvertStringToPokeColor(species.color.name),
                    title = pokemon.name.capitalize(),
                    icon = Icons.Default.ArrowBack,
                    pokemon = pokemon
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(ConvertStringToPokeColor(species.color.name))) {
                    Row(modifier = Modifier
                        .padding(top=8.dp, start=16.dp)) {
                        pokemon?.types?.forEach { type ->
                            Type(type = type)
                        }
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
                                        painter = painterResource(id = R.drawable.pokemon3),
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
                        CardNavigation("about", pokemon = pokemon, species = species, evolutionChainDetails = evolutionChain)
                    }
                }
            }
        )
    }
}

@Composable
fun CardNavigation(
    page: String,
    pokemon: Pokemon,
    species: PokemonSpecies,
    evolutionChainDetails: EvolutionChainDetails
) {

    Card(modifier = Modifier
        .padding(top = 0.dp)
        .offset(y = 10.dp)
        .fillMaxSize(), backgroundColor = White, shape = MaterialTheme.shapes.large, elevation = -2.dp) {

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
                .background(MaterialTheme.colors.secondaryVariant), color = MaterialTheme.colors.secondaryVariant)
            Row(Modifier.weight(5f)) {
                Crossfade(targetState = currentPage) { screen ->
                    when (screen) {
                        "about" -> Description(pokemon = pokemon, species = species)
                        "stats" -> Stats(pokemon)
                        "moves" -> Moves(pokemon)
                        "evolution" -> EvolutionChain(evolutionChainDetails)
                    }
                }
            }
        }

    }
}

@Composable
fun Stats(pokemon: Pokemon) {
    LazyColumn {
        items(pokemon.stats) { stat ->
            SingleStat(name = stat.stat.name, value = stat.base_stat)
        }
    }
}

@Composable
fun SingleStat(name: String, value: Int, max: Int = 100) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(vertical = 5.dp, horizontal = 8.dp)
        .fillMaxWidth()
        .height(35.dp)) {

        Column(modifier = Modifier
            .weight(3f)
            .fillMaxHeight()) {
            Text(text = name.capitalize(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Normal)
        }
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()) {
            Text(
                text = value.toString(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier
            .weight(4f)
            .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {

            Canvas(modifier = Modifier
                .fillMaxSize()
                .height(30.dp)){
                var width = value/(max/200.0)
                val canvasSize = Size((width.toFloat()), 30F)
                drawRect(
                    color = Color.Gray,
                    Offset(0F,10F),
                    size = canvasSize
                )
            }
        }
    }
}

@Composable
fun Moves(pokemon: Pokemon) {

    Text(text = "TODO: include Moves",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun EvolutionChain(evolutionChainDetails: EvolutionChainDetails) {
    var evolves = evolutionChainDetails.chain.evolves_to
    var species = evolutionChainDetails.chain.species
    var cnt = 0;
    LazyColumn() {
        item {
            while(evolves.size > 0) {
                EvolutionEntry(evolveEntry = evolves.get(0), speciesName = species?.name.toString())
                if(evolves.get(0).evolves_to.size > 0) {
                    species = evolves.get(0).species
                    evolves = evolves.get(0).evolves_to
                    cnt++
                } else break

            }
        }
    }
}

@Composable
fun EvolutionEntry(evolveEntry: EvolveEntry, speciesName: String) {
    Row() {
        Text(text = speciesName.capitalize(),
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
        val text = if (evolveEntry.evolution_details.get(0).min_level != null) {
            evolveEntry.evolution_details.get(0).min_level.toString() +" Min Level" }
        else {
            evolveEntry.evolution_details.get(0).min_happiness?.toString() + "Min Happiness"
        }
        Column(modifier = Modifier
            .padding(8.dp)
            .weight(1f)) {
            Text(text = text,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
            Text(text = evolveEntry.evolution_details.get(0).trigger.name.capitalize(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(text = evolveEntry.species.name.capitalize(),
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
    }
}

@Composable
fun Description(pokemon: Pokemon, species:  PokemonSpecies) {
    val text = if (species.flavor_text_entries.isNotEmpty()) {
        species.flavor_text_entries.get(7).flavor_text.replace("[\n\r]".toRegex(), " ") }
    else "loading..."
    LazyColumn(modifier = Modifier.padding(8.dp)) {
       item {
            Text(text = text,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp))
            }

        item {
            HeightWidth(pokemon = pokemon)
        }
        item {
            Row() {
                Text(text = "Egg Groups:",
                    color = transparentGrey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                species.egg_groups.forEach {
                    Text(text = it.name.capitalize(),
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun HeightWidth(pokemon: Pokemon) {
    Card( modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), elevation = 2.dp, shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()) {
            Column(Modifier.weight(1f)) {
                Text(text = "Height",
                    color = transparentGrey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )

                Text(text = (pokemon.height/10.0).toString() + " cm",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

            }
            Column(Modifier.weight(1f)) {
                Text(text = "Weight",
                    color = transparentGrey,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Text(text = (pokemon.weight/10.0).toString()+" kg",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
    }
}

@Composable
fun ExpandingCard(title: String, content: @Composable () -> Unit) {

    var expanded = remember { mutableStateOf(false)}
    Card (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), backgroundColor = Color.White, shape = MaterialTheme.shapes.medium) {
        Column(
            Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {
            Text(text = title,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(8.dp))
            if (expanded.value) {

                content()
                IconButton(onClick = { expanded.value = false }) {
                    Icon(painter = painterResource(id = R.drawable.arrow_drop_up), contentDescription = null)
                }

            } else {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(painter = painterResource(id = R.drawable.arrow_drop_down), contentDescription = null)
                }
            }
        }
    }
}