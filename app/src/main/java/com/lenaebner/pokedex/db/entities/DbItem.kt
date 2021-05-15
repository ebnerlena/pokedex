package com.lenaebner.pokedex.db.entities

import androidx.room.*

@Entity(tableName = "item")
data class DbItem(
    @PrimaryKey val itemId: Long,
    val name: String,
    val sprite: String,
    val category: String,
    val cost: Int = 0,
    val fling_power: Int? = null,
    val fling_effect: String? = null,
    val description: String,
)

@Entity(tableName = "item_effect")
data class DbItemEffect(
    @PrimaryKey(autoGenerate = true) val itemEffectId: Int = 0,
    val shortEffect: String,
    val itemId: Long
)

data class ItemWithEffects(
    @Embedded val item: DbItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "itemEffectId",
    )
    val effects: List<DbItemEffect>
)


@Entity(tableName = "item_attribute")
data class DbItemAttribute(
    @PrimaryKey(autoGenerate = true) val attributeId: Long = 0,
    val itemAttributeId: Long,
    val name: String
)


@Entity(primaryKeys = ["itemId", "itemAttributeId"], tableName = "item_attribute_cross_ref")
data class ItemAttributeCrossRef(
    val itemId: Long,
    val itemAttributeId: Long
)

data class ItemWithAttributes(
    @Embedded val item: DbItem,
    @Relation(
        parentColumn = "itemId",
        entityColumn = "itemAttributeId",
        associateBy = Junction(ItemAttributeCrossRef::class)
    )
    val attributes: List<DbItemAttribute>
)