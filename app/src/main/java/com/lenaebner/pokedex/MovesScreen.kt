package com.lenaebner.pokedex

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lenaebner.pokedex.PokedexScreen.PokemonsGrid
import com.lenaebner.pokedex.shared.Header

@Composable
fun Moves (navController: NavController) {

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Moves",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { 
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                    Text(
                        text = "Feature not ready - come back later",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 6.dp, start= 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}