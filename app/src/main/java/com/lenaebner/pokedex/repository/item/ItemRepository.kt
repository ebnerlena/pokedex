package com.lenaebner.pokedex.repository.item


import com.lenaebner.pokedex.api.ItemApi
import com.lenaebner.pokedex.api.models.ApiItem
import com.lenaebner.pokedex.db.daos.ItemDao
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val api: ItemApi,
    private val itemDb: ItemDao,
) {

    fun getItem(id: Long): Flow<Item> {
        val flows = listOf(
            itemDb.observeItem(id).distinctUntilChanged().filterNotNull(),
            itemDb.observeItemWithAttributes(id).distinctUntilChanged().filterNotNull(),
            itemDb.observeItemWithEffects(id).distinctUntilChanged().filterNotNull()
        )

        return combine(flows) {
            val dbItem = it[0] as DbItem
            val attributes = it[1] as ItemWithAttributes?
            val effects = it[2] as ItemWithEffects?


            val item = Item (
                name = dbItem.name,
                sprite = dbItem.sprite,
                id = dbItem.itemId.toInt(),
                description = dbItem.description,
                cost = dbItem.cost,
                flingPower = dbItem.fling_power,
                flingEffect = dbItem.fling_effect,
                category = dbItem.category,
                attributes = attributes?.attributes?.map { it.name } ?: emptyList(),
                effects = effects?.effects?.map{ it.shortEffect } ?: emptyList()
            )

            return@combine item
        }.distinctUntilChanged()
    }

    suspend fun persistenItem(apiItem: ApiItem) {
        itemDb.insertItem(apiItem.asDbItem())

        //Create effects and attributes if they do not exist yet
        for (effect in apiItem.effect_entries) {

            itemDb.insertEffect(
                DbItemEffect(
                    itemId = apiItem.id.toLong(),
                    shortEffect = effect.shortEffect
                )
            )
        }
        for(attribute in apiItem.attributes) {

            var dbAttribute = itemDb.getItemAttribute(attribute.name)
            val attributeId = attribute.url.split("/")[6].toLong()
            var ref = itemDb.getItemAttributeCrossRef(itemId = apiItem.id, attributeId = attributeId)

            if(dbAttribute == null) {

                itemDb.insertAttribute(
                    DbItemAttribute(
                        name = attribute.name,
                        itemAttributeId = attributeId
                    )
                )
            }
            if(ref == null) {
                itemDb.insertItemAttributeCrossRef(
                    ItemAttributeCrossRef(
                        itemAttributeId = attributeId,
                        itemId = apiItem.id.toLong()
                    )
                )
            }
        }
    }
}