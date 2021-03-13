package com.lenaebner.pokedex

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController

import com.lenaebner.pokedex.ui.theme.PokedexTheme

@Preview
@Composable
fun pokemonPreview() {
    val navC = rememberNavController()
    SinglePokemonScreen(pokemonName = "Pikachu", navController = navC)
}

@Composable
fun SinglePokemonScreen(pokemonName: String?, navController: NavController) {

    PokedexTheme() {
        Scaffold (
            topBar = {
                Header(navController = navController,
                    textColor = Color.White,
                    backgroundColor = MaterialTheme.colors.primary,
                    title = pokemonName.orEmpty(),
                    icon = R.drawable.arrow_left_long_white )
            },
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.pokemon3), contentDescription = null, alignment = Alignment.CenterEnd, modifier = Modifier
                            .padding(8.dp)
                            .height(80.dp)
                            .width(80.dp))
                    }
                    LazyColumn(content = {
                        item {
                            ExpandingCard(title = "Base Stats", body = "here are my stats")
                        }
                        item {
                            ExpandingCard(title = "About", body = "some description")
                        }
                        item {
                            ExpandingCard(title = "Types", body = "all about my tpyes")
                        }
                        item {
                            ExpandingCard(title = "Evolution Chain", body = "thats how I grew up")
                        }
                    })

                }

            }
        )
    }

}

@Composable
fun ExpandingCard(title: String, body: String) {

    var expanded = remember { mutableStateOf(false)}
    Card (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), backgroundColor = Color.White, shape = MaterialTheme.shapes.medium) {
        Column(
            Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {
            Text(text = title, color = MaterialTheme.colors.secondaryVariant, style = MaterialTheme.typography.h3, modifier = Modifier.padding(8.dp))
            if (expanded.value) {
                Text(text = body, color = MaterialTheme.colors.secondaryVariant, style = MaterialTheme.typography.body1)
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
