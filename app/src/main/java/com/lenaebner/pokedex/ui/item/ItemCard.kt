package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.repository.Item

@Composable
fun ItemCard(item: Item, modifier: Modifier) {
    Row(modifier = modifier){
        Card(modifier = Modifier
            .padding(top = 0.dp)
            .offset(y = 10.dp)
            .fillMaxSize(),
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.large,
            elevation = 2.dp
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item {
                    ItemAbout(item = item)
                }
                item {
                    ItemEffects(item = item)
                }
                item {
                    ItemCosts(item = item)
                }
                item {
                    ItemAttributes(item = item, modifier = Modifier.weight(1.2f))
                }
            }
        }
    }
}