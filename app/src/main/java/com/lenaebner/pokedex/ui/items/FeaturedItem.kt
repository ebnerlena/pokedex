package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.repository.item.ItemPreview
import com.lenaebner.pokedex.ui.theme.pokeGrey


@Composable
fun FeaturedItem(
    item: ItemPreview,
    modifier: Modifier = Modifier,
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = pokeGrey
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = item.onClick)
        ) {
            ItemCardHeader(
                item = item,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.End)
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ItemCosts(
                    item = item,
                    modifier = Modifier
                        .weight(2f)
                        .align(Alignment.Top)
                )
                ItemImage(
                    item = item,
                    modifier = Modifier
                        .weight(3f)
                )
            }
        }
    }
}
