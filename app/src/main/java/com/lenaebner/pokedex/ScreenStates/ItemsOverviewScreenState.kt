package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.Item

sealed class ItemsOverviewScreenState {
    object Loading: ItemsOverviewScreenState()
    data class Content(
        val items: MutableList<Item>
    ) : ItemsOverviewScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemsOverviewScreenState()
}
