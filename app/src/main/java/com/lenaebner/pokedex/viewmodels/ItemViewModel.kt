package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.ItemScreenState
import com.lenaebner.pokedex.api.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableLiveData<ItemScreenState>()
    val uiState : LiveData<ItemScreenState> = _uiState

    init {
        val itemName : String = savedStateHandle["name"] ?: "master-ball"
        fetchItem(itemName)
    }

    private fun createContentState(item: Item) {
        _uiState.postValue(
            ItemScreenState.Content(
                item = item,
            )
        )
    }

    private fun fetchItem(name: String) {

        _uiState.postValue(ItemScreenState.Loading)

        viewModelScope.launch {

            try {
                val item = withContext(Dispatchers.IO){
                    ApiController.itemsApi.getItem(name)
                }
                createContentState(item)
            } catch (exception: Throwable) {
                _uiState.postValue(ItemScreenState.Error(
                    message = exception.message ?: "A error occured when fetching the item",
                    retry = { fetchItem(name) }
                ))
            }
        }
    }

}