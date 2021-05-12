package com.lenaebner.pokedex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lenaebner.pokedex.db.daos.*
import com.lenaebner.pokedex.db.entities.*

@Database(entities = [
    DbPokemonPreview::class,
    DbPokemon::class,
    DbPokemonType::class,
    PokemonTypeCrossRef::class,
    DbPokemonAbility::class,
    DbPokemonStat::class,
    PokemonPreviewTypeCrossRef::class,
    PokemonAbilityCrossRef::class,
    DbSpeciesEggGroup::class,
    DbSpecies::class,
    SpeciesEggGroupCrossRef::class,
    DbEvolvingPokemons::class,
    SpeciesEvolvingPokemonsCrossRef::class,
    PokemonSpeciesCrossRef::class
                     ],
    exportSchema = true, version = 1)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao
    abstract fun pokemonPreviewDao() : PokemonPreviewDao
    abstract fun pokemonSpeciesDao() : PokemonSpeciesDao
    abstract fun pokemonTypeDao() : PokemonTypeDao
}