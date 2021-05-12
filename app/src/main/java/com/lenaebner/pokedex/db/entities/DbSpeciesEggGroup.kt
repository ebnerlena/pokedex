package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "egg_group")
data class DbSpeciesEggGroup(
    @PrimaryKey val eggGroupId: Int,
    val name: String
)

@Entity(primaryKeys = ["speciesId", "eggGroupId"], tableName = "species_egg_group_cross_ref")
data class SpeciesEggGroupCrossRef(
    val eggGroupId: Long,
    val speciesId: Long
)

data class SpeciesWithEggGroups(
    @Embedded val species: DbSpecies,
    @Relation(
        parentColumn = "speciesId",
        entityColumn = "eggGroupId",
        associateBy = Junction(SpeciesEggGroupCrossRef::class)
    )
    val eggGroups: List<DbSpeciesEggGroup>
)