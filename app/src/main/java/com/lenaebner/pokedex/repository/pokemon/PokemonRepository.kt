package com.lenaebner.pokedex.repository.pokemon

import com.lenaebner.pokedex.api.PokemonApi
import com.lenaebner.pokedex.api.models.ApiEvolutionChainDetails
import com.lenaebner.pokedex.db.PokedexDatabase
import com.lenaebner.pokedex.db.daos.PokemonDao
import com.lenaebner.pokedex.db.daos.PokemonSpeciesDao
import com.lenaebner.pokedex.db.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val db: PokedexDatabase,
    private val api: PokemonApi,
    private val pokemonDb: PokemonDao,
    private val speciesDb: PokemonSpeciesDao,
) {

    private val repositoryScope = CoroutineScope(context = Dispatchers.IO)

    fun getPokemon(id: Long, speciesId: Long): Flow<SinglePokemonComplete> {
        repositoryScope.launch {
            //db.clearAllTables()
            refreshPokemon(id)
        }

        val pokemonFlows = listOf(
            pokemonDb.observePokemon(id).distinctUntilChanged().filterNotNull(),
            speciesDb.observeSpecies(speciesId).distinctUntilChanged().filterNotNull(),
            pokemonDb.observePokemonWithAbilities(id).distinctUntilChanged().filterNotNull(),
            pokemonDb.observePokemonWithStats(id).distinctUntilChanged().filterNotNull(),
            pokemonDb.observePokemonWithTypes(id).distinctUntilChanged().filterNotNull(),
            speciesDb.observeSpeciesWithEvolvingPokemons(speciesId).distinctUntilChanged().filterNotNull(),
            speciesDb.observeSpeciesWithEggGroups(speciesId).distinctUntilChanged().filterNotNull()
        )

       return combine(pokemonFlows) {
           val pokemon = it[0] as DbPokemon
           val species = it[1] as DbSpecies
            val abilites = it[2] as PokemonWithAbilities?
            val stats = it[3] as PokemonWithStats?
            val types = it[4] as PokemonWithTypes?
            val evolvingPokemons = it[5] as SpeciesWithEvolvingPokemons?
            val eggGroups = it[6] as SpeciesWithEggGroups?

            val _pokemon = Pokemon(
                id = pokemon.pokemonId,
                name = pokemon?.name,
                sprite = pokemon?.sprite,
                description = pokemon?.description,
                weight = pokemon?.weight,
                height = pokemon?.height,
                abilities = abilites?.abilites?.map { it.name },
                types = types?.types?.map { it.name },
                stats = stats?.stats?.map { it.asUiStat() },
                speciesId = speciesId.toInt()
            )

            val _species = Species(
                name = species?.name,
                id = species?.speciesId,
                egg_groups = eggGroups?.eggGroups?.map { it.name } ?: emptyList(),
                flavor_text_entry = species?.description,
                evolvingPokemons = evolvingPokemons?.evolvingPokemons?.map { it.asEvolvingPokemon() } ?: emptyList(),
                color = species?.color,
                genera = species?.genera
            )

            return@combine SinglePokemonComplete(
                pokemon = _pokemon,
                species = _species
            )
        }.distinctUntilChanged()

    }

    private suspend fun refreshPokemon(id: Long) {

        val persitentPokemon = pokemonDb.getPokemon(id)

        if(persitentPokemon == null) {
            val apiPoke = api.getPokemon(id)
            val speciesId = apiPoke.species.url.split("/")[6].toLong()
            val species = api.getPokemonSpecies(speciesId)
            val evolutionChainId = species.evolution_chain.url.split("/")[6].toLong()
            val evolutionChain = api.getEvolutionChain(evolutionChainId)

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

            pokemonDb.insertPokemonSpeciesCrossRef( PokemonSpeciesCrossRef(
                poke.pokemonId.toLong(), species.id.toLong()
            ))

            // insert egggroups
            for(eggGroup in species.egg_groups) {
                speciesDb.insertEggGroup(eggGroup = eggGroup.asDbSpeciesEggGroup())

                speciesDb.insertSpeciesEggGroupCrossRef(
                    SpeciesEggGroupCrossRef(
                        eggGroupId = eggGroup.url.split("/")[6].toLong(),
                        speciesId = species.id.toLong()
                    )
                )
            }
            // insert stats
            for(stat in apiPoke.stats) {
                pokemonDb.insertStat(
                    stat.asDbPokemonStat(apiPoke.id)
                ) }

            // insert abilites
            for(ability in apiPoke.abilities) {

                val abilityId = ability.ability.url.split("/")[6].toLong()
                val dbAbility = pokemonDb.getPokemonAbility(id)

                if(dbAbility == null) {
                    pokemonDb.insertAbility(ability = ability.asDbPokemonAbility())
                    pokemonDb.insertPokemonAbilityCrossRef(
                        PokemonAbilityCrossRef(
                            abilityId = abilityId,
                            pokemonId = apiPoke.id
                        )
                    )
                }
            }

            // insert evolving pokemons
            insertEvolutionChainPokemons(evolutionChain, speciesId = speciesId)
        }
    }

    private suspend fun insertEvolutionChainPokemons(evolutionChainDetails: ApiEvolutionChainDetails, speciesId: Long): List<EvolvingPokemons> {

        val pokemons: MutableList<EvolvingPokemons> = mutableListOf()
        var evolves = evolutionChainDetails.chain.evolves_to
        var species = evolutionChainDetails.chain.species
        var entries = evolves

        try {
            entries.forEachIndexed { idx, evolveStep, ->
                evolves = evolutionChainDetails.chain.evolves_to
                while (evolves.isNotEmpty()) {
                    val evolveEntry = evolves[idx]
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
                            from = UiBasicPokemon(
                                id= pokeFrom.id.toInt(),
                                name = pokeFrom.name,
                                sprite = pokeFrom.sprites?.other?.artwork?.sprite.toString(),
                                species = pokeFrom.species.name,
                                speciesId = pokeFrom.species.url.split("/")[6].toLong(),
                                onClick = { }
                            ),

                            to = UiBasicPokemon(
                                id= pokeTo.id.toInt(),
                                name = pokeTo.name,
                                sprite = pokeTo.sprites?.other?.artwork?.sprite.toString(),
                                species = pokeTo.species.name,
                                speciesId = pokeTo.species.url.split("/")[6].toLong(),
                                onClick = {}
                            ),
                            trigger = triggerText,
                            id = evolutionChainDetails.id
                        )
                    )

                    speciesDb.insertEvolvingPokemons(pokemons[pokemons.size-1].asDbEvolvingPokemon())

                    // find db entry to get id for reference
                    val dbEvolvingPokemons = speciesDb.getEvolvingPokemons(fromId = pokeFrom.id, toId = pokeTo.id, trigger = triggerText)

                    speciesDb.insertSpeciesEvolvingPokemonCrossRef(
                        SpeciesEvolvingPokemonsCrossRef(
                            speciesId = speciesId,
                            evolvingPokemonId = dbEvolvingPokemons.evolvingPokemonId.toLong()
                        )
                    )

                    species = evolves[idx].species
                    evolves = evolves[idx].evolves_to
                }
            }


        } catch (exception: Throwable) {

        }
        return pokemons.toList()
    }

}
