package com.lenaebner.pokedex.viewmodels


import androidx.lifecycle.*
import androidx.paging.*
import com.lenaebner.pokedex.db.PokedexDatabase
import com.lenaebner.pokedex.db.entities.DbItem
import com.lenaebner.pokedex.repository.ItemsRemoteMediator
import com.lenaebner.pokedex.repository.item.ItemPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ItemsViewModel @Inject constructor(
    val database: PokedexDatabase,
    private val itemsRemoteMediator: ItemsRemoteMediator
) : ViewModel() {

    private val _actions = Channel<ItemsScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val items : Flow<PagingData<ItemPreview>> = Pager(
        pagingSourceFactory = { database.itemDao().getItemsPaged() },
        config = PagingConfig(pageSize = 25, initialLoadSize = 50),
        remoteMediator = itemsRemoteMediator
    ).flow.map{
        it.map{ item -> item.asItemPreview() }
    }.cachedIn(viewModelScope)

    private fun DbItem.asItemPreview() = ItemPreview(
        id = itemId.toInt(),
        name = name,
        category = category,
        sprite = sprite,
        cost = cost,
        onClick = { viewModelScope.launch { _actions.send(ItemsScreenAction.Navigate("item/${itemId}")) } }
    )

    sealed class ItemsScreenAction {
        object NavigateBack : ItemsScreenAction()
        data class Navigate(val destination: String): ItemsScreenAction()
    }
}
