package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.models.ApiItem
import com.lenaebner.pokedex.db.entities.DbItem

data class ItemPreview(
    val category: String,
    val cost: Int = 0,
    val name: String ="",
    val id: Int = 1,
    val sprite: String,
    var onClick: () -> Unit
)

data class Item(
    val category: String,
    val cost: Int = 0,
    val name: String ="",
    val id: Int = 1,
    val sprite: String,
    val description: String,
    val flingEffect: String?,
    val flingPower: Int?
)

fun DbItem.asItem() = Item(
    id = itemId.toInt(),
    name = name,
    description = description,
    cost = cost,
    flingEffect = fling_effect,
    flingPower = fling_power,
    sprite = sprite,
    category = category

)

fun ApiItem.asDbItem() = DbItem(
    name = names[7].name,
    category = category.name,
    cost = cost,
    fling_effect = fling_effect,
    fling_power = fling_power,
    sprite = sprites.default,
    itemId = id.toLong(),
    description = flavor_text_entries[7].text
)