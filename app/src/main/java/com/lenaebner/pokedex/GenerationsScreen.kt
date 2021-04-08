package com.lenaebner.pokedex

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.Debug
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.*
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.ui.theme.*
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Preview
@Composable
fun GenerationsPreview() {

    PokedexTheme {
        Generations(navController = rememberNavController())
    }
}

@Composable
fun Generations(navController: NavController) {

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Generations",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { GenerationsGrid(navController = navController) }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenerationsGrid(navController: NavController) {

    val scope = rememberCoroutineScope()
    var list: MutableState<GenerationsList> = mutableStateOf(GenerationsList())

    val task = scope.launch {
        list.value = withContext(Dispatchers.IO) {
            ApiController.pokeApi.getGenerations()
        }
    }

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(list.value.results) { g ->
            FeaturedGeneration(
                generation = g,
                navController = navController,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun FeaturedGeneration(
    generation: GenerationPreview,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("generation/${generation.name}") {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
        ) {
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = generation.name.toUpperCase(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier
                        .weight(3f)
                        .padding(top = 6.dp, start = 12.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.End).padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(3f).padding(2.dp)) {
                    CoilImage(
                        data = "https://www.itl.cat/pngfile/big/0-438_added-by-geocen-pokemon-all.jpg",
                        contentDescription = "Generation",
                        loading = {
                            Image(
                                painter = painterResource(id = R.drawable.pokemon1),
                                contentDescription = "Fallback Image"
                            )
                        },
                        requestBuilder = {
                            transformations(CircleCropTransformation())
                        },
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .heightIn(min = 20.dp, max = 80.dp)
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                    )
                }
            }
        }
    }
}

