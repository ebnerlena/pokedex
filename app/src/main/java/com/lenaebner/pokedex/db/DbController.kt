package com.lenaebner.pokedex.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbController {

    @Singleton
    @Provides
     fun pokedexDb(@ApplicationContext context: Context) =  Room
         .databaseBuilder(
             context,
             PokedexDatabase::class.java,
             "pokemons.db"
         )
        .createFromAsset("prefetched.db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun pokePreviewDao(db: PokedexDatabase) = db.pokemonPreviewDao()

    @Singleton
    @Provides
    fun pokemonDao(db: PokedexDatabase) = db.pokemonDao()

    @Singleton
    @Provides
    fun pokemonSpeciesDao(db: PokedexDatabase) = db.pokemonSpeciesDao()

    @Singleton
    @Provides
    fun pokemonTypeDao(db: PokedexDatabase) = db.pokemonTypeDao()

    @Singleton
    @Provides
    fun itemDao(db: PokedexDatabase) = db.itemDao()
}

