package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSprite
import retrofit2.http.GET
import retrofit2.http.Path

interface SpritesApi {
    //https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon
    @GET("other/dream-world/{id}.svg")
    suspend fun getPokemonSprite(@Path("id") id: Int): PokemonSprite

}