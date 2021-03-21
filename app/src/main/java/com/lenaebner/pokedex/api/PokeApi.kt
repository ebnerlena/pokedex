package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Pokemon

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("id") name: String): Pokemon

    @GET("pokemon/")
    suspend fun getPokemons(): List<Pokemon>
}