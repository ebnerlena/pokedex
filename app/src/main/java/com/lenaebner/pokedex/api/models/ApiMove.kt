package com.lenaebner.pokedex.api.models

import com.squareup.moshi.Json

data class ApiMove (
    val accuracy: Int,
    val contest_type: ContestType,
    val damage_class: DamageClass,
    val effect_entries: List<EffectEntry>,
    val flavor_text_entries: List<ApiFlavorTextEntry> = emptyList(),
    val learned_by_pokemon : List<PokemonPreview>,
    val category: ApiCategory,
    val name: String,
    val power: Int?,
    val pp : Int?,
    val priority: Int?
)

data class MovePreview(
    val name: String,
    val url: String
)

data class ContestType(
    val name: String
)
data class DamageClass(
    val name: String
)

data class EffectEntry(
    val effect: String,
    @Json(name = "short_effect") val shortEffect: String
)