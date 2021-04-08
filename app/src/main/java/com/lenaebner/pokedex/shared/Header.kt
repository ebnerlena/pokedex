package com.lenaebner.pokedex.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.lenaebner.pokedex.api.models.Pokemon


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
