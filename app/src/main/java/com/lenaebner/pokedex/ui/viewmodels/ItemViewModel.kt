package com.lenaebner.pokedex.viewmodels

import androidx.lifecycle.*
import com.lenaebner.pokedex.ScreenStates.ItemScreenState
import com.lenaebner.pokedex.repository.item.Item
import com.lenaebner.pokedex.repository.item.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemRepository: ItemRepository
    ) : ViewModel()
{

    private val _actions = Channel<ItemScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = MutableStateFlow<ItemScreenState>(ItemScreenState.Loading)
    val uiState: StateFlow<ItemScreenState> = _uiState

    init {
        val id = savedStateHandle["itemId"] ?: 0

        viewModelScope.launch {
            itemRepository.getItem(id.toLong()).collect { item ->
                _uiState.emit(
                    ItemScreenState.Content(
                        item = item,
                        backClicked = { viewModelScope.launch { _actions.send(ItemScreenAction.NavigateBack) }}
                    )
                )
            }
        }
    }

    sealed class ItemScreenAction {
        object NavigateBack : ItemScreenAction()
    }
}


