package com.lenaebner.pokedex.shared

import PokedexTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun LoadingSpinner() {
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
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(100.dp)
                            .width(100.dp)
                            .background(Color.White),
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun loadingSpinnerPreview() {
    LoadingSpinner()
}