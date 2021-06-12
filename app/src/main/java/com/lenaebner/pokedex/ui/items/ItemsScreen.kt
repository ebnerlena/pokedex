package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.ItemScreen.ItemsGrid
import com.lenaebner.pokedex.repository.item.ItemPreview
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.LoadingSpinner
import com.lenaebner.pokedex.viewmodels.ItemsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@Composable
fun Items(items: LazyPagingItems<ItemPreview>, backClicked: () -> Unit, lazyListState: LazyListState) {

    Scaffold (
        topBar = {
            Header(
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = MaterialTheme.colors.background,
                title = "Items",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack,
                backClicked = backClicked
            )
        },
        content = {
            if (items.loadState.refresh == LoadState.Loading) {
                LoadingSpinner()
            } else {
                ItemsGrid(items = items, lazyListState)
            }
        }
    )
}

@ExperimentalPagingApi
@Composable
fun ItemsScreen(lazyListState: LazyListState, lazyPagingItems : LazyPagingItems<ItemPreview>) {

    //val lazyPagingItems = vm.items.collectAsLazyPagingItems()
    val navController = ActiveNavController.current

    Items(items = lazyPagingItems, backClicked = { navController.navigateUp() }, lazyListState)
}