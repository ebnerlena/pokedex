package com.lenaebner.pokedex.viewmodels


import androidx.lifecycle.*
import com.lenaebner.pokedex.ScreenStates.ItemsOverviewScreenState
import com.lenaebner.pokedex.repository.item.ItemPreview
import com.lenaebner.pokedex.repository.item.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    val itemRepository: ItemRepository
) : ViewModel() {

    private val _actions = Channel<ItemsScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = MutableStateFlow<ItemsOverviewScreenState>(ItemsOverviewScreenState.Loading)

    val uiState : StateFlow<ItemsOverviewScreenState> = _uiState

    init {
        viewModelScope.launch {
            itemRepository.getItems(1,30).collect {
                val items = it.map { item ->
                    ItemPreview(
                        id = item.itemId.toInt(),
                        name = item.name,
                        category = item.category,
                        sprite = item.sprite,
                        cost = item.cost,
                        onClick = { viewModelScope.launch { _actions.send(ItemsScreenAction.Navigate("item/${item.itemId}")) } }
                    )
                }

                _uiState.emit(
                    ItemsOverviewScreenState.Content(
                        items = items,
                        backClicked = { viewModelScope.launch { _actions.send(ItemsScreenAction.NavigateBack) } }
                    )
                )
            }
        }
    }

    sealed class ItemsScreenAction {
        object NavigateBack : ItemsScreenAction()
        data class Navigate(val destination: String): ItemsScreenAction()
    }
}
