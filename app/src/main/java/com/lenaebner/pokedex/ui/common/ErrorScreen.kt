package com.lenaebner.pokedex.shared

import PokedexTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(errorMessage: String, retry: () -> Unit) {
    PokedexTheme {

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)) {
            Row(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(modifier = Modifier
                    .align(Alignment.CenterVertically).fillMaxWidth()) {
                    Text(errorMessage, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Button(retry, modifier = Modifier.padding(8.dp).background(MaterialTheme.colors.primary).align(Alignment.CenterHorizontally)) {
                        Text("Retry", color = MaterialTheme.colors.secondaryVariant)
                    }

                }

            }
        }
    }
}