package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Pokemon

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon

    @GET("pokemon/?limit=50")
    suspend fun getPokemons(): PokemonList

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpecies

    @GET("pokemon-species/{id}")
    suspend fun getPokemonColor(@Path("id") id: Int): PokemonColor

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Int): EvolutionChainDetails
}