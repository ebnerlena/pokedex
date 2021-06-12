package com.lenaebner.pokedex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lenaebner.pokedex.api.PokemonApi
import com.lenaebner.pokedex.db.daos.PokemonPreviewDao
import com.lenaebner.pokedex.db.entities.PokemonPreviewWithTypes
import com.lenaebner.pokedex.repository.pokemon.PokedexRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class PokedexRemoteMediator @Inject constructor(
    private val api: PokemonApi,
    private val database: PokemonPreviewDao,
    private val pokedexRepository: PokedexRepository
) : RemoteMediator<Int, PokemonPreviewWithTypes>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonPreviewWithTypes>
    ): MediatorResult {

        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.pokemon?.pokemonId?.toInt() ?: 0
            }

            val response = api.getPokemons(
                limit = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                },
                offset = offset
            )
            coroutineScope {
                launch(Dispatchers.IO) {
                    response.results.map {
                        async {
                            var dbPokemon = database.getPokemon(it.name)
                            if (dbPokemon == null) {
                                var apiPokemon = api.getPokemon(it.name)
                                pokedexRepository.persistPokemon(apiPoke = apiPokemon)
                            }
                        }
                    }.awaitAll()
                }
            }

            return MediatorResult.Success(endOfPaginationReached = response.next == null)
        } catch(e: Throwable) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return if (database.getNumberOfPokemons() > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
}
