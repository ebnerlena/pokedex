package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.Item

@Composable
fun ItemAttributes(item: Item, modifier: Modifier) {
    Row {
        Text(
            text = "Attributes:",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6,
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        Column(modifier = Modifier
            .weight(2f)
            .padding(vertical = 8.dp)) {
            item.attributes.forEach {
                Text(
                    text = it.name.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

    }
}