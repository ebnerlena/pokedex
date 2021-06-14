package com.lenaebner.pokedex.api.models

data class ApiItem(
    val attributes: List<Attribute> = emptyList(),
    val category: ApiCategory = ApiCategory(name = ""),
    val cost: Int = 0,
    val name: String ="",
    val id: Int = 1,
    val fling_power: Int? = null,
    val fling_effect: ApiFlingEffect? = null,
    val effect_entries: List<EffectEntry> = emptyList(),
    val flavor_text_entries: List<FlavorTextEntryItem> = emptyList(),
    val sprites: ItemSprite = ItemSprite(),
    val names: List<ItemName> = emptyList(),
)

data class ApiFlingEffect(
    val name: String
)

data class ApiItemPreview(
    val name: String,
    val url: String,
)

data class ApiItemsList (
    val count: Int = 0,
    val results: List<ApiItemPreview>,
    val next: String?
)

data class Attribute(
    val name: String,
    val url: String
)

data class ItemSprite(
    val default: String = ""
)

data class ItemName(
    val language: Language,
    val name: String = ""
)

data class Language(
    val name: String
)
