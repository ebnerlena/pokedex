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
fun ItemAbout(item: Item) {
    Column {
        Text(text = "About",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )

        Text(text = item.description,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(8.dp).animateContentSize()
        )
    }
}