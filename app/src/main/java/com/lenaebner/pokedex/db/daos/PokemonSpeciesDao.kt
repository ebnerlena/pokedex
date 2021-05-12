package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSpeciesDao {
    @Insert
    suspend fun insertSpecies(species: DbSpecies)

    @Update
    suspend fun updateSpecies(species: DbSpecies)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun getPokemonWithSpecies(id: Long): Flow<PokemonWithSpecies>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun getSpeciesWithEvolvingPokemons(id: Long): Flow<SpeciesWithEvolvingPokemons>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun getSpeciesWithEggGroups(id: Long): Flow<SpeciesWithEggGroups>
}