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

@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { Home(navController = navController) }
        composable("pokedex") { Pokedex(navController = navController) }
        composable("generations") { Generations(navController = navController) }
        composable("moves") { Moves(navController = navController) }
        composable("items") { Items(navController = navController) }
        composable(
            "pokemon/{pokemonName}",
            arguments = mutableStateListOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            SinglePokemonScreen(
                pokemonName = backStackEntry.arguments?.getString("pokemonName"),
                navController = navController
            )
        }
        composable(
            "generation/{name}",
            arguments = mutableStateListOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            SingleGeneration(
                name = backStackEntry.arguments?.getString("name"),
                navController = navController
            )
        }
    }
}

@Composable
fun Header(navController: NavController, textColor: Color, backgroundColor: Color, title: String, icon: ImageVector, iconTint: Color = Color.White, pokemon: Pokemon? = null) {

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
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(start=8.dp, bottom = 8.dp).weight(5f)
                )
                if(pokemon != null) {
                    Text(
                        text = '#'+pokemon.id.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(end=8.dp, bottom = 8.dp).weight(1f)
                    )
                }
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
