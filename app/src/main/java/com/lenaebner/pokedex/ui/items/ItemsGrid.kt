package com.lenaebner.pokedex.ItemScreen


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lenaebner.pokedex.ItemsScreen.FeaturedItem
import com.lenaebner.pokedex.repository.item.ItemPreview


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsGrid(items: LazyPagingItems<ItemPreview>, lazyListState: LazyListState) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(4.dp),
        state = lazyListState
    ) {
        items(items.itemCount) { index ->

            val listItem = items.getAsState(index = index).value

            if(listItem != null) {
                FeaturedItem(
                    item = listItem,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 6.dp)
                        .background(MaterialTheme.colors.background)
                        .animateContentSize()
                )
                Spacer(modifier = Modifier.size(4.dp))
            }

        }
        item {
            if (items.loadState.append == LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(8.dp)
                    .animateContentSize()
                )
            }
        }
    }
}