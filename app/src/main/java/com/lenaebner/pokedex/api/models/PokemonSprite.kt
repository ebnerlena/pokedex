package com.lenaebner.pokedex.api.models

import com.squareup.moshi.Json

data class PokemonSprite(
    val other: ArtworkSprite
)

data class DreamWorldSprite(
   val dream_world: Sprite
)
data class ArtworkSprite(
    @Json(name="official-artwork")val artwork: Sprite
)

data class Sprite(
    @Json(name = "front_default")val sprite: String
)