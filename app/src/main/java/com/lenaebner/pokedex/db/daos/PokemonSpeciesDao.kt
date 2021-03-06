package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSpeciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: DbSpecies)

    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun getSpecies(id: Long): DbSpecies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvolvingPokemons(evolvingEntry: DbEvolvingPokemons)

    @Query("SELECT * FROM evolving_pokemons WHERE from_basicPokemonId = :fromId AND to_basicPokemonId = :toId AND `trigger` LIKE :trigger")
    fun getEvolvingPokemons(fromId: Long, toId: Long, trigger: String): DbEvolvingPokemons

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesEvolvingPokemonCrossRef(ref: SpeciesEvolvingPokemonsCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesEggGroupCrossRef(ref: SpeciesEggGroupCrossRef)

    @Query("SELECT * FROM egg_group WHERE eggGroupId LIKE :eggGroupId")
    suspend fun getEggGroup(eggGroupId: Long): DbSpeciesEggGroup

    @Transaction
    @Query("SELECT * FROM species_egg_group_cross_ref WHERE speciesId LIKE :speciesId AND eggGroupId LIKE :eggGroupId")
    suspend fun getSpeciesEggGroupCrossRef(speciesId: Long, eggGroupId: Long): SpeciesEggGroupCrossRef


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEggGroup(eggGroup: DbSpeciesEggGroup)

    @Update
    suspend fun updateSpecies(species: DbSpecies)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithSpecies(id: Long): Flow<PokemonWithSpecies>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun observeSpecies(id: Long): Flow<DbSpecies>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun observeSpeciesWithEvolvingPokemons(id: Long): Flow<SpeciesWithEvolvingPokemons>

    @Transaction
    @Query("SELECT * FROM species WHERE speciesId LIKE :id")
    fun observeSpeciesWithEggGroups(id: Long): Flow<SpeciesWithEggGroups>
}