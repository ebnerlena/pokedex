package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.repository.item.ItemPreview


sealed class ItemsOverviewScreenState {
    object Loading: ItemsOverviewScreenState()
    data class Content(
        val items: List<ItemPreview>,
        val backClicked : () -> Unit
    ) : ItemsOverviewScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemsOverviewScreenState()
}
