package com.lenaebner.pokedex.api.models

import com.squareup.moshi.Json

data class EffectEntry(
    val effect: String,
    @Json(name = "short_effect")val shortEffect: String
)