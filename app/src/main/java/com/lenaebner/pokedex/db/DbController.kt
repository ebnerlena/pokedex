package com.lenaebner.pokedex.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbController {

    @Provides
     fun pokedexDb(@ApplicationContext context: Context) =  Room
         .databaseBuilder(
             context,
             PokedexDatabase::class.java,
             "pokemons.db"
         )
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    fun pokePreviewDao(db: PokedexDatabase) = db.pokemonPreviewDao()

    @Provides
    fun pokemonDao(db: PokedexDatabase) = db.pokemonDao()

    @Provides
    fun pokemonSpeciesDao(db: PokedexDatabase) = db.pokemonSpeciesDao()

    @Provides
    fun pokemonTypeDao(db: PokedexDatabase) = db.pokemonTypeDao()

    @Provides
    fun itemDao(db: PokedexDatabase) = db.itemDao()
}

