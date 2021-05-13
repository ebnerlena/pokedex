package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "ability")
data class DbPokemonAbility (
    @PrimaryKey val abilityId: Long,
    val name: String
)

@Entity(primaryKeys = ["pokemonId", "abilityId"], tableName = "pokemon_ability_cross_ref")
data class PokemonAbilityCrossRef(
    val pokemonId: Long,
    val abilityId: Long
)

data class PokemonWithAbilities(
    @Embedded val pokemon: DbPokemon,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "abilityId",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val abilites: List<DbPokemonAbility>
)