package com.lenaebner.pokedex

import com.lenaebner.pokedex.api.ItemApi
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiController {

    @Singleton
    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .client(client)
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun pokeApi(retrofit: Retrofit) = retrofit.create(PokemonApi::class.java)

    @Singleton
    @Provides
    fun itemApi(retrofit: Retrofit) = retrofit.create(ItemApi::class.java)

}