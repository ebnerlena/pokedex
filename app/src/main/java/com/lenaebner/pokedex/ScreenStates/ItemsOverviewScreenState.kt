package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.ItemOverview

sealed class ItemsOverviewScreenState {
    object Loading: ItemsOverviewScreenState()
    data class Content(
        val items: List<ItemOverview>,
        val backClicked : () -> Unit
    ) : ItemsOverviewScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemsOverviewScreenState()
}
