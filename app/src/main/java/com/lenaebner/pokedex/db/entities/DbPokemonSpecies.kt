package com.lenaebner.pokedex.db.entities

import androidx.room.*
import com.lenaebner.pokedex.api.models.*

@Entity(tableName = "species")
data class DbSpecies(
    @PrimaryKey val speciesId: Int,
    val name: String,
    val color: String,
    val description: String,
    val genera: String,
)

@Entity(primaryKeys = ["pokemonId", "speciesId"], tableName = "pokemon_species_cross_ref")
data class PokemonSpeciesCrossRef(
    val pokemonId: Long,
    val speciesId: Long
)

data class PokemonWithSpecies(
    @Embedded val pokemon: DbPokemon,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "speciesId",
        associateBy = Junction(PokemonSpeciesCrossRef::class)
    )
    val species: DbSpecies
)