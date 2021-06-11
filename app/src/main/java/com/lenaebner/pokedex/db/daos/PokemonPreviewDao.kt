package com.lenaebner.pokedex.db.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonPreviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: DbPokemonPreview)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(vararg pokemons: DbPokemonPreview)

    @Transaction
    @Query("SELECT * FROM pokemon_preview LIMIT 200")
    fun observePokePreviews(): Flow<List<PokemonPreviewWithTypes>>

    @Transaction
    @Query("SELECT * FROM  pokemon_type where typeId = :id")
    fun observeTypeWithPokemons(id: Long): Flow<List<TypeWithPokemons>>

    @Transaction
    @Query("SELECT * FROM  pokemon_preview where name LIKE :query LIMIT 4")
    fun findPokemons(query: String): Flow<List<DbPokemonPreview>>

    @Update
    suspend fun updatePokemon(pokemon: DbPokemonPreview)

    @Query("DELETE from pokemon_preview")
    suspend fun deleteAll()

    @Query("SELECT * from pokemon_preview ORDER BY pokemonId DESC")
    suspend  fun getAll(): List<DbPokemonPreview>

    @Query("SELECT Count(pokemonId) from pokemon_preview")
    suspend  fun getNumberOfPokemons(): Int

    @Query("SELECT * from pokemon_preview ORDER BY pokemonId ASC")
    fun getPokemonPreviewsPaged(): PagingSource<Int, PokemonPreviewWithTypes>

    @Transaction
    @Query("SELECT * FROM pokemon_preview WHERE pokemonId LIKE :pokemonId")
    suspend fun getPokemonPreviewWithTypes(pokemonId: Long): PokemonPreviewWithTypes

    @Query("SELECT * from pokemon_preview WHERE name = :name")
    suspend fun getPokemon(name: String): DbPokemonPreview

}
