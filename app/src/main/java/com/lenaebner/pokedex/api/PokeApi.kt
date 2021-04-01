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

    @GET("item?limit={limit}")
    suspend fun getItems(@Path("limit") limit: Int): ItemsList

    @GET("item/{id}")
    suspend fun getItem(@Path("id") id: Int): Item

    @GET("item/{name}")
    suspend fun getItem(@Path("name") name: String): Item

    @GET("generation")
    suspend fun getGenerations(): GenerationsList

    @GET("generation/{id}")
    suspend fun getGeneration(@Path("id") id: Int): Generation

    @GET("generation/{name}")
    suspend fun getGeneration(@Path("name") name: String): Generation

    @GET("move?limit={limit}")
    suspend fun getMoves(@Path("limit") limit: Int): MoveList

    @GET("move/{id}")
    suspend fun getMove(@Path("id") id: Int): Move

    @GET("move/{name}")
    suspend fun getMove(@Path("name") name: String): Move
}