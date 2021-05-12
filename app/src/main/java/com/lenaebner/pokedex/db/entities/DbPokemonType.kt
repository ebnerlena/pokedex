package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "pokemon_type")
data class DbPokemonType(
    @PrimaryKey val typeId: Long,
    val name: String
)

@Entity(primaryKeys = ["pokemonId", "typeId"], tableName = "pokemon_type_cross_ref")
data class PokemonTypeCrossRef(
    val pokemonId: Long,
    val typeId: Long
)

data class PokemonWithTypes(
    @Embedded val pokemon: DbPokemon,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<DbPokemonType>
)

data class PokemonPreviewWithTypes(
    @Embedded val pokemon: DbPokemonPreview,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<DbPokemonType>
)