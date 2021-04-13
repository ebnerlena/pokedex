package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.ItemsOverviewScreenState
import com.lenaebner.pokedex.api.models.Item
import kotlinx.coroutines.*

class ItemsViewModel : ViewModel() {

    private val _uiState = MutableLiveData<ItemsOverviewScreenState>()
    val uiState : LiveData<ItemsOverviewScreenState> = _uiState
    private var _items: MutableList<Item> = mutableListOf()

    init {
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
    }

    private fun fetchItems(offset: Int=0, limit: Int=20) {

        _uiState.postValue(ItemsOverviewScreenState.Loading)

        viewModelScope.launch {

            try {

                val itemsOverview = withContext(Dispatchers.IO) {
                    ApiController.itemsApi.getItems(offset = offset,limit = limit)
                }
                val items = itemsOverview.results.map {
                    async {
                        _items.add(
                            (Dispatchers.IO){
                            ApiController.itemsApi.getItem(it.name)
                            }
                        )
                    }
                }.awaitAll()

                createContentState()

            } catch (exception: Throwable) {

                _uiState.postValue(
                    ItemsOverviewScreenState.Error(
                        message = exception.message ?: "An error occured when trying to fetch pokemons",
                        retry = { fetchItems() }
                    )
                )
            }
        }
    }
}