package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "stat")
data class DbPokemonStat (
    @PrimaryKey val statId: Long,
    val name: String,
    val effort: Int,
    val base_stat: Int,
    val statPokemonId: Long
)

/*
@Entity(primaryKeys = ["pokemonId", "statId"], tableName = "pokemon_stat_cross_ref")
data class PokemonStatCrossRef(
    val pokemonId: Long,
    val statId: Long
) */

data class PokemonWithStats(
    @Embedded val pokemon: DbPokemon,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "statPokemonId",
    )
    val types: List<DbPokemonStat>
)