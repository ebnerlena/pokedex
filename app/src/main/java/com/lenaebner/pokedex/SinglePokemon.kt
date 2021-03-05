package com.lenaebner.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController

import com.lenaebner.pokedex.ui.theme.PokedexTheme


@Composable
fun SinglePokemonScreen(pokemonName: String?) {

    val navController  = rememberNavController()
    PokedexTheme() {
        Surface {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = pokemonName.orEmpty().capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h3,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp).weight(0.2f),
                )
                Spacer(modifier = Modifier.size(16.dp))

                Button(onClick =  {
                    navController.navigate("home") {
                        launchSingleTop = true
                        popUpTo = navController.graph.startDestination
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier.padding(32.dp)
                ){
                    Text(
                        text = "Back to Home",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun pokemonPreview() {
    SinglePokemonScreen(pokemonName = "pikachu")
}