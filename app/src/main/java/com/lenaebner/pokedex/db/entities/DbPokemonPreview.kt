package com.lenaebner.pokedex.db.entities

import androidx.room.*


@Entity(tableName = "pokemon_preview")
data class DbPokemonPreview(
    @PrimaryKey val pokemonId: Long,
    val name: String,
    val sprite: String,
    val color: String,
    val speciesId: Long
)
