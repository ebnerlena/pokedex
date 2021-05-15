package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "evolving_pokemons")
data class DbEvolvingPokemons(
    @PrimaryKey(autoGenerate = true) val evolvingPokemonId: Int = 0,
    val trigger: String,
    @Embedded(prefix = "from_") val from: BasicPokemon,
    @Embedded(prefix = "to_") val to: BasicPokemon,
)

data class BasicPokemon(
    val basicPokemonId: Int,
    val name: String,
    val speciesName: String,
    val sprite: String,
    val speciesId: Long
)

@Entity(primaryKeys = ["speciesId", "evolvingPokemonId"], tableName = "species_evolving_pokemons_cross_ref")
data class SpeciesEvolvingPokemonsCrossRef(
    val speciesId: Long,
    val evolvingPokemonId: Long
)

data class SpeciesWithEvolvingPokemons(
    @Embedded val species: DbSpecies,
    @Relation(
        parentColumn = "speciesId",
        entityColumn = "evolvingPokemonId",
        associateBy = Junction(SpeciesEvolvingPokemonsCrossRef::class)
    )
    val evolvingPokemons: List<DbEvolvingPokemons>
)