package com.lenaebner.pokedex.ui.screenstates

sealed class UiState<out T : Any> {
    object Loading : UiState<Nothing>()
    data class Content<out T : Any>(val content: T) : UiState<T>()
    data class Error(val message: String? = null) : UiState<Nothing>()
}
