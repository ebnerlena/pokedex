package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lenaebner.pokedex.ItemsScreen.FeaturedItem
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.api.models.ItemOverview
import com.lenaebner.pokedex.viewmodels.ItemsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsGrid(items: List<ItemOverview>) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(items) { item ->
            FeaturedItem(
                item = item,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}