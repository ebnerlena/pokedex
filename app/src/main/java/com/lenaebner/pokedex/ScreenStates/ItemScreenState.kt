package com.lenaebner.pokedex.ScreenStates

import com.lenaebner.pokedex.api.models.Item

sealed class ItemScreenState {
    object Loading: ItemScreenState()
    data class Content(
        val item: Item,
        val backClicked: () -> Unit
    ) : ItemScreenState()
    data class Error(val message: String, val retry: () -> Unit): ItemScreenState()
}