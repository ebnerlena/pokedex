package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTypeDao {

    @Insert
    suspend fun insertType(type: DbPokemonType)

    @Insert
    suspend fun insertPokemonTypeCrossRef(ref: PokemonTypeCrossRef)

    @Query("SELECT * from pokemon_type WHERE name = :name")
    suspend fun getType(name: String) : DbPokemonType

    @Query("SELECT * from pokemon_type_cross_ref WHERE pokemonId = :pokemonId AND typeId = :typeId")
    suspend fun getPokemonTypeRef(pokemonId: Long, typeId: Long) : PokemonTypeCrossRef

    @Query("SELECT * FROM pokemon_type")
    fun observeAll(): Flow<List<DbPokemonType>>

    @Query("SELECT * FROM pokemon_type")
    suspend fun getAll(): List<DbPokemonType>
}