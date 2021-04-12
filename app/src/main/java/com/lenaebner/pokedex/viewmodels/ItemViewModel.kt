package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.ItemScreenState
import com.lenaebner.pokedex.api.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemViewModel : ViewModel() {

    private val _uiState = MutableLiveData<ItemScreenState>()
    val uiState : LiveData<ItemScreenState> = _uiState

    fun createContentState(item: Item) {
        _uiState.postValue(
            ItemScreenState.Content(
                item = item,
            )
        )
    }

    fun fetchItem(name: String) {

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