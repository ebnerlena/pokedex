package com.lenaebner.pokedex.api.models

data class ItemOverview(
    val category: Category = Category(name = ""),
    val cost: Int = 0,
    val name: String ="",
    val id: Int = 1,
    val sprites: ItemSprite = ItemSprite(),
    val names: List<ItemName> = emptyList(),
    var onClick: () -> Unit
)