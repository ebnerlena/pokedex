package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.viewmodels.ItemsViewModel

sealed class ItemsOverviewScreenState {
    object Loading: ItemsOverviewScreenState()
    data class Content(
        val items: List<ItemsViewModel.ItemOverview>
    ) : ItemsOverviewScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemsOverviewScreenState()
}
