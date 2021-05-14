package com.lenaebner.pokedex.db.daos

import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: DbPokemon)

    @Update
    suspend fun updatItem(item: DbPokemon)

    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    suspend fun geItem(id: Long): DbPokemon

    @Transaction
    @Query("SELECT * FROM pokemon WHERE pokemonId LIKE :id")
    fun observeItem(id: Long): Flow<DbPokemon>

}