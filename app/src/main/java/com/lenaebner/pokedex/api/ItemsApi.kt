package com.lenaebner.pokedex.api

import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.api.models.ItemsList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemsApi {

    @GET("item")
    suspend fun getItems(@Query("offset") offset: Int? = null, @Query("limit") limit: Int? = null): ItemsList

    @GET("item/{id}")
    suspend fun getItem(@Path("id") id: Int): Item

    @GET("item/{name}")
    suspend fun getItem(@Path("name") name: String): Item
}