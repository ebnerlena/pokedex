package com.lenaebner.pokedex.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.ItemsOverviewScreenState
import com.lenaebner.pokedex.api.models.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow

class ItemsViewModel : ViewModel() {

    private val _actions = Channel<ItemsAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _items = flow{
        emit(fetchItems())
    }

    private val itemsOverview = _items.map {
        it.map{
            ItemOverview(
                id = it.id,
                name = it.name,
                category = it.category,
                cost = it.cost,
                sprites = it.sprites,
                onClick = { viewModelScope.launch { _actions.send(ItemsAction.Navigate("item/${it.name}")) } }
            )
        }

    }

    private val _uiState = flow {
        emit(ItemsOverviewScreenState.Loading)
        try {
            itemsOverview.collect {
                emit(ItemsOverviewScreenState.Content(items = it))
            }
        } catch(ex: Throwable) {
            emit(ItemsOverviewScreenState.Error(
                message = "An error occured while fetching the items",
                retry = {}
            ))
        }

    }
    val uiState =  _uiState.asLiveData()


    /* init {
        fetchItems(0,20)
        fetchItems(20,20)
        fetchItems(40,20)
    }

    private fun createContentState() {

        _items.sortBy { item -> item.id }

        _uiState.postValue(
            ItemsOverviewScreenState.Content(
                items = _items
            )
        )
    } */

    private suspend fun fetchItems(offset: Int=0, limit: Int=20): List<Item> {

        //_uiState.postValue(ItemsOverviewScreenState.Loading)
        var _items = emptyList<Item>()

            val itemsOverview = withContext(Dispatchers.IO) {
                ApiController.itemsApi.getItems(offset = offset, limit = limit)
            }
            var items = itemsOverview.results.map {

                    withContext(Dispatchers.IO) {
                        ApiController.itemsApi.getItem(it.name)
                    }
                }
        Log.d("foo", items.toString())
        return  items
    }

    sealed class ItemsAction {
        data class Navigate(val destination: String) : ItemsAction()
    }

    data class ItemOverview(
        val category: Category = Category(name = ""),
        val cost: Int = 0,
        val name: String ="",
        val id: Int = 1,
        val sprites: ItemSprite = ItemSprite(),
        val names: List<ItemName> = emptyList(),
        var onClick: () -> Unit
    )
}
