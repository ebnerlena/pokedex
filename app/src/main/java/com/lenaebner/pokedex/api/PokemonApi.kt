package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Long): ApiPokemon

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): ApiPokemon

    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): ApiPokemonList

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Long): ApiSpecies

    @GET("pokemon-species/{id}")
    suspend fun getPokemonColor(@Path("id") id: Long): PokemonColor

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Long): ApiEvolutionChainDetails

    @GET("move")
    suspend fun getMoves(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): ApiMoveList

    @GET("move/{id}")
    suspend fun getMove(@Path("id") id: Long): ApiMove

    @GET("move/{name}")
    suspend fun getMove(@Path("name") name: String): ApiMove
}