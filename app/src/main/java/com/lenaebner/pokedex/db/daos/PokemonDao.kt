package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert
    suspend fun insertPokemon(pokemon: DbPokemon)

    @Update
    suspend fun updatePokemon(pokemon: DbPokemon)

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun getPokemon(id: Long): Flow<DbPokemon>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun getPokemonWithAbilities(id: Long): Flow<PokemonWithAbilities>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun getPokemonWithStats(id: Long): Flow<PokemonWithStats>

    @Transaction
    @Query("SELECT * from pokemon WHERE pokemonId = :id")
    suspend fun getPokemonWithTypes(id: Long) : PokemonWithTypes
}