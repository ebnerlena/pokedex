package com.lenaebner.pokedex

import com.lenaebner.pokedex.api.PokemonApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApiController {

    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
   fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun retrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .client(client)
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun pokeApi(retrofit: Retrofit) = retrofit.create(PokemonApi::class.java)

   // @Provides
   // fun itemsApi(retrofit: Retrofit) = retrofit.create(ItemsApi::class.java)

}