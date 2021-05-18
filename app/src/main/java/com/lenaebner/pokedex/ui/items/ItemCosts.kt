package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.item.ItemPreview


@Composable
fun ItemCosts(item: ItemPreview, modifier: Modifier) {
    Column(modifier = modifier
        .padding(start = 8.dp, end = 4.dp, top = 16.dp)
    ) {
        Text(
            text = item.cost.toString(),
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(top = 6.dp, start= 8.dp)
                .align(Start)
        )
        Text(
            text = "Cost",
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .align(Start)
                .padding(start=8.dp)

        )
    }
}