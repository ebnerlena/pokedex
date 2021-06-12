package com.lenaebner.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.lenaebner.pokedex.db.PokedexDatabase
import com.lenaebner.pokedex.db.daos.PokemonPreviewDao
import com.lenaebner.pokedex.db.entities.PokemonPreviewWithTypes
import com.lenaebner.pokedex.db.entities.PokemonWithTypes
import com.lenaebner.pokedex.repository.PokedexRemoteMediator
import com.lenaebner.pokedex.repository.pokemon.PokedexRepository
import com.lenaebner.pokedex.repository.pokemon.asPokemonPreview
import com.lenaebner.pokedex.ui.pokedex.PokemonWithColor
import com.lenaebner.pokedex.ui.screenstates.PokedexScreenState
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

    //private val _uiState = MutableStateFlow<PokedexScreenState>(PokedexScreenState.Loading)

    //val uiState : StateFlow<PokedexScreenState> = _uiState

    val pokemons : Flow<PagingData<PokemonWithColor>> = Pager(
        pagingSourceFactory = { pokedexDb.getPokemonPreviewsPaged() },
        config = PagingConfig(pageSize = 25, initialLoadSize = 50),
        remoteMediator = pokedexRemoteMediator
    ).flow.map{
        it.map{ p -> p.asPokemonWithColor() }
    }.cachedIn(viewModelScope)

    val backClicked = { viewModelScope.launch { _actions.send(PokedexScreenAction.NavigateBack)
        Log.d("foo", "bakc clicked")}
    }

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

    /* init {


        viewModelScope.launch {

            pokedexRepository.getPokemons(1, 200).collect {
                val pokemons = it.map { p ->
                    PokemonWithColor(
                        name = p.name,
                        color = p.color,
                        id = p.id,
                        types = p.types,
                        sprite = p.sprite,
                        onClick = {
                            viewModelScope.launch {
                                _actions.send(PokedexScreenAction.PokemonClicked("pokemon/${p.id}?speciesId=${p.speciesId}"))
                            }
                        }
                    )
                }

                _uiState.emit(
                    PokedexScreenState.Content(
                        pokemonsWithColor = pokemons,
                        backClicked = { viewModelScope.launch { _actions.send(PokedexScreenAction.NavigateBack) } }
                    )
                )
            }
        }
    } */

    sealed class PokedexScreenAction {
        object NavigateBack : PokedexScreenAction()
        data class PokemonClicked(val destination: String): PokedexScreenAction()
    }
}