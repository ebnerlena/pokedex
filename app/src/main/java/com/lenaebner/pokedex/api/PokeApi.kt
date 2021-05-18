package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Pokemon

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon

    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): PokemonList

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpecies

    @GET("pokemon-species/{id}")
    suspend fun getPokemonColor(@Path("id") id: Int): PokemonColor

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Int): EvolutionChainDetails

    @GET("move")
    suspend fun getMoves(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): MoveList

    @GET("move/{id}")
    suspend fun getMove(@Path("id") id: Int): Move

    @GET("move/{name}")
    suspend fun getMove(@Path("name") name: String): Move
}