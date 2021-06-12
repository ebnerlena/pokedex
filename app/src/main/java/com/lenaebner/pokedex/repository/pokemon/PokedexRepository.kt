package com.lenaebner.pokedex.repository.pokemon

import android.util.Log
import androidx.paging.*
import com.lenaebner.pokedex.api.PokemonApi
import com.lenaebner.pokedex.api.models.ApiPokemon
import com.lenaebner.pokedex.db.daos.PokemonPreviewDao
import com.lenaebner.pokedex.db.daos.PokemonTypeDao
import com.lenaebner.pokedex.db.entities.DbPokemonPreview
import com.lenaebner.pokedex.db.entities.DbPokemonType
import com.lenaebner.pokedex.db.entities.PokemonTypeCrossRef
import com.lenaebner.pokedex.repository.PokedexRemoteMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class PokedexRepository @Inject constructor(
    private val api: PokemonApi,
    private val pokemonDb: PokemonPreviewDao,
    private val typeDb: PokemonTypeDao,
) {

    fun findPokemons(query: String): Flow<List<SearchPokemonPreview>> {
        return pokemonDb.findPokemons(query)
            .distinctUntilChanged()
            .filterNotNull()
            .map {
                it.map { p ->
                    p.asSearchPokemonPreview()
                }
            }
    }

    suspend fun persistPokemon(apiPoke: ApiPokemon){
        val id = apiPoke.species.url.split("/")[6].toLong()
        val dbColor = api.getPokemonColor(id)
        val poke = DbPokemonPreview(
            name = apiPoke.name,
            pokemonId = apiPoke.id,
            color = dbColor.color.name,
            sprite = apiPoke.sprites?.other?.artwork?.sprite.toString(),
            speciesId = id
        )
        pokemonDb.insertPokemon(poke)

        //Create types and references if they do not exist yet
        for (type in apiPoke.types) {

            var dbType = typeDb.getType(type.type.name)
            val id = type.type.url.split("/")[6].toLong()
            var ref = typeDb.getPokemonTypeRef(typeId = id, pokemonId = apiPoke.id)

            if(dbType == null) {
                typeDb.insertType(DbPokemonType(id, type.type.name))
            }

            if(ref == null) {
                typeDb.insertPokemonTypeCrossRef(
                    PokemonTypeCrossRef(
                        pokemonId = apiPoke.id,
                        typeId = id
                    )
                )
            }
        }
    }
}