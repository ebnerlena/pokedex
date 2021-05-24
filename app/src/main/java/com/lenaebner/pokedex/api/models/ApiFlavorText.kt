package com.lenaebner.pokedex.api.models

data class ApiFlavorTextEntry(
    val flavor_text: String = "",
    val language: Language
)

data class FlavorTextEntryItem(
    val text: String = "",
    val language: Language
)