package com.lenaebner.pokedex.api.models

data class Move (
    val accuracy: Int,
    val contest_type: ContestType,
    val damage_class: DamageClass,
    val effect_entries: List<EffectEntry>,
    val flavor_text_entries: List<FlavorTextEntry> = emptyList(),
    val learned_by_pokemon : List<PokemonPreview>,
    val category: Category,
    val name: String,
    val power: Int?,
    val pp : Int?,
    val priority: Int?
)

data class MovePreview(
    val name: String,
    val url: String
)

data class MoveList(
    val count: Int = 0,
    val results: List<MovePreview>
)

data class ContestType(
    val name: String
)
data class DamageClass(
    val name: String
)
