package com.lenaebner.pokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.lenaebner.pokedex.db.daos.PokemonPreviewDao
import com.lenaebner.pokedex.db.entities.PokemonPreviewWithTypes
import com.lenaebner.pokedex.repository.PokedexRemoteMediator
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokedexDb : PokemonPreviewDao,
    private val pokedexRemoteMediator: PokedexRemoteMediator
): ViewModel() {

    private val _actions = Channel<PokedexScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val pokemons : Flow<PagingData<PokemonWithColor>> = Pager(
        pagingSourceFactory = { pokedexDb.getPokemonPreviewsPaged() },
        config = PagingConfig(pageSize = 25, initialLoadSize = 50),
        remoteMediator = pokedexRemoteMediator
    ).flow.map{
        it.map{ p -> p.asPokemonWithColor() }
    }.cachedIn(viewModelScope)

    val backClicked = { viewModelScope.launch { _actions.send(PokedexScreenAction.NavigateBack) } }

    fun PokemonPreviewWithTypes.asPokemonWithColor() = PokemonWithColor(
        name = pokemon.name,
        color = pokemon.color,
        id = pokemon.pokemonId,
        types = types.map { it.name },
        sprite = pokemon.sprite,
        onClick = {
            viewModelScope.launch {
                _actions.send(PokedexScreenAction.PokemonClicked(
                    destination = "pokemon/${pokemon.pokemonId}?speciesId=${pokemon.speciesId}")
                )
            }
        }
    )

    sealed class PokedexScreenAction {
        object NavigateBack : PokedexScreenAction()
        data class PokemonClicked(val destination: String): PokedexScreenAction()
    }
}