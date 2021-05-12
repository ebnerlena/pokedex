package com.lenaebner.pokedex.PokedexScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.theme.transparentWhite


@Composable
fun Type(type: String) {
    Card(shape= MaterialTheme.shapes.medium, modifier = Modifier
        .padding(1.dp),
        backgroundColor= transparentWhite
    ) {
        Text(text = type.capitalize(), color = Color.White, modifier = Modifier
            .padding(vertical = 3.dp, horizontal = 6.dp), style = MaterialTheme.typography.caption,)
    }
}