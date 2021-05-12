package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSpeciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: DbSpecies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesEggGroupCrossRef(ref: SpeciesEggGroupCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEggGroup(eggGroup: DbSpeciesEggGroup)

    @Update
    suspend fun updateSpecies(species: DbSpecies)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithSpecies(id: Long): Flow<PokemonWithSpecies>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun observeSpeciesWithEvolvingPokemons(id: Long): Flow<SpeciesWithEvolvingPokemons>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun observeSpeciesWithEggGroups(id: Long): Flow<SpeciesWithEggGroups>
}