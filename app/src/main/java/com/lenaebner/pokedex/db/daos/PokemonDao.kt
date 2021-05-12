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
    suspend fun insertPokemonSpeciesCrossRef(ref: PokemonSpeciesCrossRef)

    @Update
    suspend fun updatePokemon(pokemon: DbPokemon)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun getPokemon(id: Long): DbPokemon

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemon(id: Long): Flow<DbPokemon>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithAbilities(id: Long): Flow<PokemonWithAbilities>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observePokemonWithStats(id: Long): Flow<PokemonWithStats>

    @Transaction
    @Query("SELECT * from pokemon WHERE pokemonId = :id")
    suspend fun getPokemonWithTypes(id: Long) : PokemonWithTypes
}