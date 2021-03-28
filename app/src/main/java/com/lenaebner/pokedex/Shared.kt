package com.lenaebner.pokedex

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import org.json.JSONObject

private const val apiResponse = "{\"count\":1118,\"next\":\"https://pokeapi.co/api/v2/pokemon/?offset=20&limit=20\",\"previous\":null,\"results\":[{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"},{\"name\":\"ivysaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/2/\"},{\"name\":\"venusaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/3/\"},{\"name\":\"charmander\",\"url\":\"https://pokeapi.co/api/v2/pokemon/4/\"},{\"name\":\"charmeleon\",\"url\":\"https://pokeapi.co/api/v2/pokemon/5/\"},{\"name\":\"charizard\",\"url\":\"https://pokeapi.co/api/v2/pokemon/6/\"},{\"name\":\"squirtle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/7/\"},{\"name\":\"wartortle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/8/\"},{\"name\":\"blastoise\",\"url\":\"https://pokeapi.co/api/v2/pokemon/9/\"},{\"name\":\"caterpie\",\"url\":\"https://pokeapi.co/api/v2/pokemon/10/\"},{\"name\":\"metapod\",\"url\":\"https://pokeapi.co/api/v2/pokemon/11/\"},{\"name\":\"butterfree\",\"url\":\"https://pokeapi.co/api/v2/pokemon/12/\"},{\"name\":\"weedle\",\"url\":\"https://pokeapi.co/api/v2/pokemon/13/\"},{\"name\":\"kakuna\",\"url\":\"https://pokeapi.co/api/v2/pokemon/14/\"},{\"name\":\"beedrill\",\"url\":\"https://pokeapi.co/api/v2/pokemon/15/\"},{\"name\":\"pidgey\",\"url\":\"https://pokeapi.co/api/v2/pokemon/16/\"},{\"name\":\"pidgeotto\",\"url\":\"https://pokeapi.co/api/v2/pokemon/17/\"},{\"name\":\"pidgeot\",\"url\":\"https://pokeapi.co/api/v2/pokemon/18/\"},{\"name\":\"rattata\",\"url\":\"https://pokeapi.co/api/v2/pokemon/19/\"},{\"name\":\"raticate\",\"url\":\"https://pokeapi.co/api/v2/pokemon/20/\"}]}"

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { Home(navController = navController) }
        composable("pokedex") { Pokedex(navController = navController) }
        composable("pokemon/{pokemonName}",
            arguments = mutableStateListOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            SinglePokemonScreen(pokemonName = backStackEntry.arguments?.getString("pokemonName"), navController = navController )
        }
    }
}

@Composable
fun Header(navController: NavController, textColor: Color, backgroundColor: Color, title: String, icon: ImageVector, iconTint: Color = Color.White) {

    TopAppBar(modifier = Modifier.height(90.dp), backgroundColor = backgroundColor) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        navController.navigate("home") {
                            launchSingleTop = true
                            popUpTo = navController.graph.startDestination
                        }
                    }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint
                    )
                }
            }
            Row() {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(start=8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AppBarPreview() {
    PokedexTheme() {
        AppBar(
            textColor = MaterialTheme.colors.secondaryVariant,
            backgroundColor = Color.White,
            title = "Pokedex",
            icon = R.drawable.arrow_left_long_grey
        )
    }
}

@Composable
fun AppBar(textColor: Color, backgroundColor: Color, title: String, icon: Int) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .height(100.dp)
                    .width(30.dp),
            )
        },
        title = {
            Text(
                text = title,
                color = textColor,
                style = MaterialTheme.typography.h2
            )
        },
        backgroundColor = backgroundColor,
    )
}
