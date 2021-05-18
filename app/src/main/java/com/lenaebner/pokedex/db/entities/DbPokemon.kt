package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "pokemon")
data class DbPokemon(
    @PrimaryKey val pokemonId: Int,
    val name: String,
    val sprite: String,
    val description: String,
    val height: Int = 0,
    val weight: Int = 0,
)






