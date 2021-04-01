package com.lenaebner.pokedex.api.models

data class Item(
    val attributes: List<Attribute>,
    val category: Category,
    val cost: Int,
    val name: String,
    val id: Int,
    val fling_power: Int?,
    val fling_effect: String?,
    val effect_entries: List<EffectEntry>,
    val flavor_text_entries: List<FlavorTextEntryItem>,
    val sprites: ItemSprite
)

data class ItemPreview(
    val name: String,
    val url: String
)


data class ItemsList (
    val count: Int = 0,
    val results: List<ItemPreview>
)

data class Attribute(
    val name: String,
    val url: String
)

data class ItemSprite(
    val default: String
)