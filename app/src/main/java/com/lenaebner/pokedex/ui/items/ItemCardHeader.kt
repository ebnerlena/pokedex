package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.ItemPreview

@Composable
fun ItemCardHeader(item: ItemPreview, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier
                .weight(3f)
                .padding(top = 6.dp, start = 12.dp)
        )
        Text(
            text = '#'+item.id.toString(),
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(top = 6.dp)
        )
    }
}
