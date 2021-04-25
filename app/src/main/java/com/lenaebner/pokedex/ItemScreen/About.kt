package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.api.models.Item

@Composable
fun ItemAbout(item: Item) {
    Column {
        Text(text = "About",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )

        val text = if (item.flavor_text_entries.isNotEmpty()) item.flavor_text_entries[7].text else " entry"

        Text(text = text,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(8.dp)
        )
    }
}