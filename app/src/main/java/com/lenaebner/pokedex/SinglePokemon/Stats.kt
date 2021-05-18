package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.Pokemon


@Composable
fun Stats(pokemon: Pokemon) {
    LazyColumn {
        items(pokemon.stats) { stat ->
            SingleStat(name = stat.stat.name, value = stat.base_stat)
        }
    }
}

@Composable
fun SingleStat(name: String, value: Int, max: Int = 100) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .padding(vertical = 2.dp, horizontal = 8.dp)
        .fillMaxWidth()
        .height(35.dp)) {

        Column(modifier = Modifier
            .weight(3f)
            .fillMaxHeight()) {
            Text(text = name.capitalize(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Normal)
        }
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()) {
            Text(
                text = value.toString(),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier
            .weight(4f)
            .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {

            Canvas(modifier = Modifier
                .fillMaxSize()
                .height(25.dp)){
                val width = value/(max/200.0)
                val canvasSize = Size((width.toFloat()), 30F)
                drawRect(
                    color = Color.Gray,
                    Offset(0F,10F),
                    size = canvasSize
                )
            }
        }
    }
}
