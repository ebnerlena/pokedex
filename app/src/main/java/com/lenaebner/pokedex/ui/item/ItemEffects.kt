package com.lenaebner.pokedex.ItemScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.item.Item

@Composable
fun ItemEffects(item: Item) {
    Column {
        Text(
            text = "Effects:",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        item.effects.forEach {
            Text(
                text = it,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp).animateContentSize()
            )
        }
    }
}