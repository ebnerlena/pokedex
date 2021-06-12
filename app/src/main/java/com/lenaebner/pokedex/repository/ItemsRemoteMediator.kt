package com.lenaebner.pokedex.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lenaebner.pokedex.api.ItemApi
import com.lenaebner.pokedex.db.daos.ItemDao
import com.lenaebner.pokedex.db.entities.DbItem
import com.lenaebner.pokedex.repository.item.ItemRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class ItemsRemoteMediator @Inject constructor(
    private val api: ItemApi,
    private val database: ItemDao,
    private val itemRepository: ItemRepository
) : RemoteMediator<Int, DbItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DbItem>
    ): MediatorResult {

        return try {

            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.itemId?.toInt() ?: 0
            }

            val response = api.getItems(
                limit = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                },
                offset = offset
            )

            coroutineScope {
                // Problem here if using async item attributes get inserted multiple times
                //launch(Dispatchers.IO) {
                    response.results.map {
                        //async {
                            var dbItem = database.getItemByName(it.name)

                            if (dbItem == null) {
                                var apiItem = api.getItem(it.name)
                                itemRepository.persistenItem(apiItem = apiItem)
                            }
                        //}
                    }
                //}.awaitAll()
            }

            return MediatorResult.Success(endOfPaginationReached = response.next == null)
        } catch(e: Throwable) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return if (database.getNumberOfItems() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}
