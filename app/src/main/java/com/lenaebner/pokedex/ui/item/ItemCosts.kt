package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.item.Item

@Composable
fun ItemCosts(item: Item) {
    Row {
        Text(
            text = "Cost:",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Text(
            text = item.cost.toString(),
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 5.dp)
        )
    }
}