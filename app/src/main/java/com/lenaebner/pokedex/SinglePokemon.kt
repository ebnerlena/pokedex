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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview
@Composable
fun pokemonPreview() {
    val navC = rememberNavController()
    SinglePokemonScreen(pokemonName = "Pikachu", navController = navC)
}

@Composable
fun SinglePokemonScreen(pokemonName: String?, navController: NavController) {

    val scope = rememberCoroutineScope()
    var pokemon: Pokemon? = null


    PokedexTheme() {

        scope.launch {
            pokemon = withContext(Dispatchers.IO){
                ApiController.pokeApi.getPokemon(26)
            }
            Log.d("foo", "Pokemon found "+pokemon?.name.orEmpty().capitalize()+" "+pokemon?.sprites)
        }
        Scaffold (

            topBar = {
                Header(navController = navController,
                    textColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primary,
                    title = pokemon?.name ?: "Pikachu",
                    icon = Icons.Default.ArrowBack,
                )
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CoilImage(
                            data = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                            contentDescription = "Pikachu",
                            loading = {
                                Image(
                                    painter = painterResource(id = R.drawable.pokemon3),
                                    contentDescription = "Fallback Image"
                                )
                            },
                            requestBuilder = {
                                transformations(CircleCropTransformation())
                            })
                    }
                    Row(modifier = Modifier.weight(3f)){
                        CardNavigation("stats")
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun CardNavPreview() {
    PokedexTheme {
        Scaffold() {
            CardNavigation("stats")
        }
    }
}

@Composable
fun CardNavigation(page: String) {
    Card(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxSize(), backgroundColor = Color.White, shape = MaterialTheme.shapes.large) {
        var currentPage by rememberSaveable { mutableStateOf(page) }
        Column {
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .weight(1f)) {
                TextButton(onClick = { currentPage = "about" }) {
                    var textColor =  if (currentPage == "about") MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
                    Text(text = "About", color = textColor)
                }
                TextButton(onClick = { currentPage = "stats" }) {
                    var textColor =  if (currentPage == "stats") MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
                    Text(text = "Base Stats", color = textColor)
                }
                TextButton(onClick = { currentPage = "evolution" }) {
                    var textColor =  if (currentPage == "evolution") MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
                    Text(text = "Evolution", color = textColor)
                }
                TextButton(onClick = { currentPage = "types" }) {
                    var textColor =  if (currentPage == "types") MaterialTheme.colors.primary else MaterialTheme.colors.secondaryVariant
                    Text(text = "Types", color = textColor)
                }
            }
            Divider(modifier = Modifier
                .height(3.dp)
                .padding(3.dp)
                .background(MaterialTheme.colors.secondaryVariant), color = MaterialTheme.colors.secondaryVariant)
            Row(Modifier.weight(5f)) {
                Crossfade(targetState = currentPage) { screen ->
                    when (screen) {
                        "about" -> Description()
                        "stats" -> Stats()
                        "types" -> Types()
                        "evolution" -> EvolutionChain()
                    }
                }
            }
        }

    }
}

@Composable
fun Stats() {
    Column() {
        SingleStat(name = "HP", value = 35)
        SingleStat(name = "Attack", value = 55)
        SingleStat(name = "Defense", value = 40)
        SingleStat(name = "Special Attack", value = 50)
        SingleStat(name = "Special Defense", value = 50)
        SingleStat(name = "Speed", value = 90)
    }
}

@Composable
fun SingleStat(name: String, value: Int, max: Int = 100) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(35.dp)) {

        Column(modifier = Modifier
            .weight(3f)
            .fillMaxHeight()) {
            Text(text = name,
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
fun Types() {
    // electric
    //https://pokeapi.co/api/v2/type/13/
    Text(text = "Electric",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun EvolutionChain() {
    Text(text = "This could be my evolution chain",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(8.dp))
}

@Composable
fun Description() {
    Text(text = "This could be my description",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(8.dp))
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