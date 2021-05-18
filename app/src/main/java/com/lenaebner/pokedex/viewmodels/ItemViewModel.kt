package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.ItemScreenState
import com.lenaebner.pokedex.api.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemViewModel(private val name: String) : ViewModel() {

    private val _actions = Channel<ItemScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = flow {
        emit(ItemScreenState.Loading)
        try {
            emit(ItemScreenState.Content(
                item = fetchItem(name),
                backClicked = { viewModelScope.launch {
                    _actions.send(ItemScreenAction.NavigateBack) }
                }
            ))

        } catch (exception: Throwable) {
            emit(ItemScreenState.Error(
                message = exception.message ?: "A error occured when fetching the item",
                retry = {  }
            ))
        }
    }
    val uiState : LiveData<ItemScreenState> = _uiState.asLiveData()

    private suspend fun fetchItem(name: String) : Item {
        return withContext(Dispatchers.IO){
            ApiController.itemsApi.getItem(name)
        }
    }

    sealed class ItemScreenAction {
        object NavigateBack : ItemScreenAction()
    }
}

class ItemViewModelFactory(private val name: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

