package com.lenaebner.pokedex.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.repository.pokemon.Pokemon


@Composable
fun Header(
    textColor: Color,
    backgroundColor: Color,
    title: String,
    icon: ImageVector,
    iconTint: Color = Color.White,
    pokemon: Pokemon? = null,
    backClicked: ()->Unit
) {
    val navController = ActiveNavController.current

    TopAppBar(modifier = Modifier.height(90.dp), backgroundColor = backgroundColor) {
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = backClicked
                    //back navigation not always working
                    // e.g when pressing back from singlepokemon gets back to homescreen instead of pokedexscreen
                    //navController.popBackStack()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth().weight(5f)
                ) {
                    val size = if(maxWidth > 200.dp) 18.sp else 16.sp
                    Text(
                        text = title,
                        fontSize = size,
                        color = textColor,
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.padding(start=8.dp, bottom = 8.dp)
                    )
                }
                /*Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(start=8.dp, bottom = 8.dp).weight(5f)
                ) */
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
