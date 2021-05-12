package com.lenaebner.pokedex.repository

import android.util.Log
import com.lenaebner.pokedex.api.PokemonApi
import com.lenaebner.pokedex.db.PokedexDatabase
import com.lenaebner.pokedex.db.daos.PokemonPreviewDao
import com.lenaebner.pokedex.db.daos.PokemonTypeDao
import com.lenaebner.pokedex.db.entities.DbPokemonPreview
import com.lenaebner.pokedex.db.entities.DbPokemonType
import com.lenaebner.pokedex.db.entities.PokemonTypeCrossRef
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
class PokedexRepository @Inject constructor(
    private val api: PokemonApi,
    private val pokemonDb: PokemonPreviewDao,
    private val typeDb: PokemonTypeDao
) {

    private val repositoryScope = CoroutineScope(context = Dispatchers.IO)

    fun getPokemons(from: Int, limit: Int) : Flow<List<PokemonPreview>> {
        repositoryScope.launch { refresh(from, limit) }

        return pokemonDb.observePokePreviews()
            .distinctUntilChanged()
            .filterNotNull()
            .map {
                it.map { p ->
                    p.asPokemonPreview()
                }
            }
    }

    private suspend fun refresh(from: Int, limit: Int) {
        val persistentPokemons = pokemonDb.getAll()

        for (i in from..from+limit) {
            var poke = persistentPokemons.find { p -> p.pokemonId.toInt() == i }

            //pokemon not yet in database
            if(poke == null) {
                val apiPoke = api.getPokemon(i.toLong())
                val id = apiPoke.species.url.split("/")[6].toLong()
                val dbColor = api.getPokemonColor(id)
                poke = DbPokemonPreview(
                    name = apiPoke.name,
                    pokemonId = apiPoke.id,
                    color = dbColor.color.name,
                    sprite = apiPoke.sprites?.other?.artwork?.sprite.toString()
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
    }
}