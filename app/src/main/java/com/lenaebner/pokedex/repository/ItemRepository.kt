package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.ItemApi
import com.lenaebner.pokedex.db.daos.ItemDao
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val api: ItemApi,
    private val itemDb: ItemDao,
) {

    private val repositoryScope = CoroutineScope(context = Dispatchers.IO)

    fun getItems(from: Int, limit: Int) : Flow<List<DbItem>> {
        repositoryScope.launch { refresh(from, limit) }

        return itemDb.observeItems()
            .distinctUntilChanged()
            .filterNotNull()
    }

    private suspend fun refresh(from: Int, limit: Int) {
        val persistentItems = itemDb.getAll()

        for (i in from..from+limit) {
            var dbItem = persistentItems.find { item ->item.itemId.toInt() == i }

            //item not yet in database
            if(dbItem == null) {
                val apiItem = api.getItem(i)

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

                    if(dbAttribute == null) {
                        val attributeId =  attribute.url.split("/")[6].toLong()
                        itemDb.insertAttribute(
                            DbItemAttribute(
                                name = attribute.name,
                                itemAttributeId = attributeId
                            )
                        )

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
    }
}