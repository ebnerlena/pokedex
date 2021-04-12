package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.Item

sealed class ItemScreenState {
    object Loading: ItemScreenState()
    data class Content(
        val item: Item
    ) : ItemScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemScreenState()
}