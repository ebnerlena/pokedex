package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.ApiItem
import com.lenaebner.pokedex.api.models.ApiItemsList
import com.lenaebner.pokedex.api.models.ApiPokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemApi {

    @GET("item")
    suspend fun getItems(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): ApiItemsList

    @GET("item")
    suspend fun getItemsList(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): ApiItemsList

    @GET("item/{id}")
    suspend fun getItem(@Path("id") id: Int): ApiItem

    @GET("item/{name}")
    suspend fun getItem(@Path("name") name: String): ApiItem
}
