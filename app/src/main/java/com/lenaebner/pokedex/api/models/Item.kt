package com.lenaebner.pokedex.api.models

data class Item(
    val attributes: List<Attribute> = emptyList(),
    val category: Category = Category(name = ""),
    val cost: Int = 0,
    val name: String ="",
    val id: Int = 1,
    val fling_power: Int? = null,
    val fling_effect: String? = null,
    val effect_entries: List<EffectEntry> = emptyList(),
    val flavor_text_entries: List<FlavorTextEntryItem> = emptyList(),
    val sprites: ItemSprite = ItemSprite(),
    val names: List<ItemName> = emptyList()
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
    val default: String = ""
)

data class ItemName(
    val name: String = ""
)