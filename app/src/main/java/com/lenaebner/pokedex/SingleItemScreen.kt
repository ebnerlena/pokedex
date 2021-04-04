package com.lenaebner.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
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
import com.lenaebner.pokedex.api.models.*
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun ItemPreview() {
    val navC = rememberNavController()
    SingleItem(itemName = "master-ball", navController = navC)
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SingleItem(itemName: String?, navController: NavController) {

    val scope = rememberCoroutineScope()
    var item by remember { mutableStateOf(Item()) }

    scope.launch {
        item = withContext(Dispatchers.IO){
            ApiController.pokeApi.getItem(itemName ?: "master-ball")
        }
    }

    PokedexTheme {

        Scaffold (
            topBar = {
                Header(navController = navController,
                    textColor = Color.White,
                    backgroundColor = transparentGrey,
                    title = item.name.toUpperCase(),
                    icon = Icons.Default.ArrowBack,
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(transparentGrey)) {
                    Row(modifier = Modifier
                        .padding(top=8.dp, start=16.dp)) {
                        Text(
                            text = item.category.name.toUpperCase(),
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
                            data = item.sprites.default,
                            contentDescription = "Item",
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
                        Card(modifier = Modifier
                            .padding(top = 0.dp)
                            .offset(y = 10.dp)
                            .fillMaxSize(),
                            backgroundColor = Color.White,
                            shape = MaterialTheme.shapes.large,
                            elevation = 2.dp
                        ) {
                            LazyColumn(modifier = Modifier.padding(8.dp)) {
                                item {
                                    Text(text = "About",
                                        color = MaterialTheme.colors.secondaryVariant,
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                val text = if (item.flavor_text_entries.isNotEmpty()) item.flavor_text_entries[7].text else " entry"
                                item {
                                    Text(text = text,
                                        color = MaterialTheme.colors.secondaryVariant,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                item {
                                    Column {
                                        Text(
                                            text = "Effects:",
                                            color = MaterialTheme.colors.secondaryVariant,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                        )
                                        item.effect_entries.forEach {
                                            Text(
                                                text = it.short_effect,
                                                color = MaterialTheme.colors.secondaryVariant,
                                                style = MaterialTheme.typography.body2,
                                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                                            )
                                        }
                                    }
                                }
                                item {
                                    Row {
                                        Text(
                                            text = "Cost:",
                                            color = MaterialTheme.colors.secondaryVariant,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                        )
                                        Text(
                                            text = item.cost.toString(),
                                            color = MaterialTheme.colors.secondaryVariant,
                                            style = MaterialTheme.typography.body2,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp)
                                        )
                                    }
                                }

                                item {
                                    Row {
                                        Text(
                                            text = "Attributes:",
                                            color = MaterialTheme.colors.secondaryVariant,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                                .weight(1.2f)
                                        )
                                        Column(modifier = Modifier.weight(2f).padding(vertical = 8.dp)) {
                                            item.attributes.forEach {
                                                Text(
                                                    text = it.name.capitalize(),
                                                    color = MaterialTheme.colors.secondaryVariant,
                                                    style = MaterialTheme.typography.body2,
                                                    fontWeight = FontWeight.Bold,
                                                )
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}