package com.lenaebner.pokedex.db.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: DbItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEffect(item: DbItemEffect)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemAttributeCrossRef(ref: ItemAttributeCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttribute(item: DbItemAttribute)

    @Update
    suspend fun updateItem(item: DbItem)

    @Query("SELECT * FROM item WHERE itemId LIKE :id")
    suspend fun getItem(id: Long): DbItem

    @Query("SELECT * FROM item WHERE name LIKE :name")
    suspend fun getItemByName(name: String): DbItem

    @Transaction
    @Query("SELECT * FROM item_attribute WHERE name LIKE :name")
    suspend fun getItemAttribute(name: String): DbItemAttribute

    @Transaction
    @Query("SELECT * FROM item_attribute_cross_ref WHERE itemId = :itemId AND itemAttributeId = :attributeId")
    suspend fun getItemAttributeCrossRef(itemId: Int, attributeId: Long): ItemAttributeCrossRef

    @Transaction
    @Query("SELECT * FROM item WHERE itemId LIKE :id")
    fun observeItem(id: Long): Flow<DbItem>

    @Transaction
    @Query("SELECT * FROM item LIMIT 100")
    fun observeItems(): Flow<List<DbItem>>

    @Transaction
    @Query("SELECT * from item ORDER BY itemId ASC")
    fun getItemsPaged(): PagingSource<Int, DbItem>

    @Transaction
    @Query("SELECT Count(itemId) from item")
    suspend  fun getNumberOfItems(): Int

    @Query("SELECT * FROM item LIMIT 100")
    suspend fun getAll(): List<DbItem>

    @Transaction
    @Query("SELECT * FROM item WHERE itemId LIKE :id")
    fun observeItemWithEffects(id: Long): Flow<ItemWithEffects>

    @Transaction
    @Query("SELECT * FROM item WHERE itemId LIKE :id")
    fun observeItemWithAttributes(id: Long): Flow<ItemWithAttributes>
}