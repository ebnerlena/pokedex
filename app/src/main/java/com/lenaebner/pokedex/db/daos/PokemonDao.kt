package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: DbPokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbilityCrossRef(ref: PokemonAbilityCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(ability: DbPokemonAbility)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStat(stat: DbPokemonStat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpeciesCrossRef(ref: PokemonSpeciesCrossRef)

    @Update
    suspend fun updatePokemon(pokemon: DbPokemon)

    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun getPokemon(id: Long): DbPokemon

    @Query("SELECT * FROM ability WHERE abilityId LIKE :id")
    suspend fun getPokemonAbility(id: Long): DbPokemonAbility

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun getPokemonWithAbility(id: Long): PokemonWithAbilities

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun getPokemonWithTypes(id: Long): PokemonWithTypes

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun getPokemonWithStats(id: Long): PokemonWithStats

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun getPokemonWithSpecies(id: Long): PokemonWithSpecies

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemon(id: Long): Flow<DbPokemon>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithSpecies(id: Long): Flow<PokemonWithSpecies>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithAbilities(id: Long): Flow<PokemonWithAbilities>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithStats(id: Long): Flow<PokemonWithStats>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId = :id")
    fun observePokemonWithTypes(id: Long): Flow<PokemonWithTypes>
}