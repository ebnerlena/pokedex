package com.lenaebner.pokedex.viewmodels


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

    private val _actions = Channel<ItemsScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _items = flow{
        emit(fetchItems())
    }

    private val itemsOverview = _items.map { it ->

        it.map { item->

            ItemOverview(
                id = item.id,
                name = item.name,
                category = item.category,
                cost = item.cost,
                sprites = item.sprites,
                names = item.names,
                onClick = { viewModelScope.launch {
                    _actions.send(ItemsScreenAction.Navigate("item/${item.name}"))
                } }
            )
        }
    }

    private val _uiState = flow {
        emit(ItemsOverviewScreenState.Loading)
        try {
            itemsOverview.collect {
                emit(ItemsOverviewScreenState.Content(
                    items = it,
                    backClicked = { viewModelScope.launch { _actions.send(ItemsScreenAction.NavigateBack) } }
                ))
            }
        } catch(ex: Throwable) {
            emit(ItemsOverviewScreenState.Error(
                message = "An error occured while fetching the items",
                retry = {}
            ))
        }
    }

    val uiState =  _uiState.asLiveData()

    private suspend fun fetchItems(offset: Int=0, limit: Int=20): List<Item> {

        val itemsOverview = withContext(Dispatchers.IO) {
            ApiController.itemsApi.getItems(offset = offset, limit = limit)
        }
        return itemsOverview.results.map {

            withContext(Dispatchers.IO) { async{
                ApiController.itemsApi.getItem(it.name) }
            }.await()
        }
    }

    sealed class ItemsScreenAction {
        object NavigateBack : ItemsScreenAction()
        data class Navigate(val destination: String): ItemsScreenAction()
    }
}
