package com.lenaebner.pokedex.repository

import com.lenaebner.pokedex.api.PokemonApi
import com.lenaebner.pokedex.api.models.ApiEvolutionChainDetails
import com.lenaebner.pokedex.api.models.EvolutionChainDetail
import com.lenaebner.pokedex.db.daos.PokemonDao
import com.lenaebner.pokedex.db.daos.PokemonSpeciesDao
import com.lenaebner.pokedex.db.daos.PokemonTypeDao
import com.lenaebner.pokedex.db.entities.DbPokemon
import com.lenaebner.pokedex.db.entities.DbPokemonPreview
import com.lenaebner.pokedex.ui.viewmodels.PokemonScreenAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val api: PokemonApi,
    private val pokemonDb: PokemonDao,
    private val speciesDb: PokemonSpeciesDao,
    private val typeDb: PokemonTypeDao
) {

    private val repositoryScope = CoroutineScope(context = Dispatchers.IO)

    suspend fun getPokemon(id: Long) : Flow<SinglePokemonComplete> {
        repositoryScope.launch { refreshPokemon( id ) }

        return pokemonDb.observePokemon(id).distinctUntilChanged().filterNotNull().map { p ->
            
            SinglePokemonComplete(
                pokemon = p.asPokemon()
                //species = ,
                //evolvingPokemons =
            )
        }
    }

    private suspend fun refreshPokemon(id: Long) {

        val persitentPokemon = pokemonDb.getPokemon(id)
        if(persitentPokemon == null) {
            val apiPoke = api.getPokemon(id)
            val species = api.getPokemonSpecies(apiPoke.id)
            val id = species.evolution_chain.url.split("/")[6].toLong()
            val evolutionChain = api.getEvolutionChain(id)

            val poke = DbPokemon (
                name = apiPoke.name,
                pokemonId = apiPoke.id.toInt(),
                sprite = apiPoke.sprites?.other?.artwork?.sprite.toString(),
                description = species.flavor_text_entries[7].flavor_text.replace("[\n\r]".toRegex(), " "), //TODO: weg lassen? wiel eh in species
                height = apiPoke.height,
                weight = apiPoke.weight
            )

            pokemonDb.insertPokemon(poke)
            speciesDb.insertSpecies(species = species.asDbSpecies())
            insertEvolutionChainPokemons(evolutionChain)
            //refresh EvolutionChainPokes insert them & species
            //db.insertEvolutionChain()
            //db.insertEvolvingPokemons()
        }

    }

    private suspend fun insertEvolutionChainPokemons(evolutionChainDetails: ApiEvolutionChainDetails): List<EvolvingPokemons> {

        val pokemons: MutableList<EvolvingPokemons> = mutableListOf()
        var evolves = evolutionChainDetails.chain.evolves_to
        var species = evolutionChainDetails.chain.species

        try {
            while (evolves.isNotEmpty()) {
                val evolveEntry = evolves[0]
                val details = evolveEntry.evolution_details[0]
                val triggerText = when (details.trigger.name) {
                    "level-up" -> if (details.min_happiness != null) "Hpy: " + details.min_happiness else "Lvl: " + evolveEntry.evolution_details[0].min_level
                    "use-item" -> details.item?.name?.capitalize() ?: "Item"
                    else -> "Level up"
                }

                val pokeFrom = withContext(Dispatchers.IO) {
                    api.getPokemon(
                        species?.name.orEmpty()
                    )
                }
                val pokeTo = withContext(Dispatchers.IO) {
                    api.getPokemon(
                        evolveEntry.species.name
                    )
                }

                pokemons.add(
                    EvolvingPokemons(
                        from = BasicPokemon(
                            id= pokeFrom.id.toInt(),
                            name = pokeFrom.name,
                            sprites = pokeFrom.sprites?.other?.artwork?.sprite.toString(),
                            species = pokeFrom.species.name,
                            onClick = { }
                        ),

                        to = BasicPokemon(
                            id= pokeTo.id.toInt(),
                            name = pokeTo.name,
                            sprites = pokeTo.sprites?.other?.artwork?.sprite.toString(),
                            species = pokeTo.species.name,
                            onClick = {repositoryScope.launch { } }
                        ),
                        trigger = triggerText
                    )
                )

                species = evolves[0].species
                evolves = evolves[0].evolves_to
            }

        } catch (exception: Throwable) {

        }
        return pokemons.toList()
    }

}
