package com.lenaebner.pokedex.viewmodels


import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.PokemonScreenState
import com.lenaebner.pokedex.api.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class PokeScreenAction {
    object NavigateBack : PokeScreenAction()
    data class pokemonClicked(val destination: String) : PokeScreenAction()
}

class PokemonViewModel(private val name: String) : ViewModel() {

    private val _actions = Channel<PokeScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = flow {
        emit(PokemonScreenState.Loading)
        try {
            val poke = fetchPokemon(name)
            val species = fetchSpecies(poke.id)
            val evolvingPokemons = fetchEvolutionChain(species.evolution_chain.url.split("/")[6].toInt())
            emit(
                PokemonScreenState.Content(
                    pokemon = poke,
                    species = species,
                    evolutionChainPokemons = evolvingPokemons,
                    backClicked = {  viewModelScope.launch { _actions.send(PokeScreenAction.NavigateBack) } },
                )
            )
        } catch (ex: Throwable) {
            emit(
                PokemonScreenState.Error(
                    message = "An error occured when fetching the requested pokemon",
                    retry = {})
            )
        }
    }

    val uiState = _uiState.asLiveData()

    private suspend fun fetchSpecies(pokemonId: Int): PokemonSpecies {

        return withContext(Dispatchers.IO) {
            ApiController.pokeApi.getPokemonSpecies(
                pokemonId
            )
        }
    }

    private suspend fun fetchEvolutionChain(speciesId: Int): MutableList<EvolvingPokemons> {

        val evolutionChain = withContext(Dispatchers.IO) {
            async { ApiController.pokeApi.getEvolutionChain(speciesId) }
        }.await()

        //fetch pokemons for Evolution Chain seperatly
        return fetchEvolutionChainPokemons(evolutionChainDetails = evolutionChain)
    }

    private suspend fun fetchPokemon(name: String): Pokemon {
        return  withContext(Dispatchers.IO) {
            async {
                ApiController.pokeApi.getPokemon(name)
            }
        }.await()
    }

    private suspend fun fetchEvolutionChainPokemons(evolutionChainDetails: EvolutionChainDetails): MutableList<EvolvingPokemons> {

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
                    ApiController.pokeApi.getPokemon(
                        species?.name.orEmpty()
                    )
                }
                val pokeTo = withContext(Dispatchers.IO) {
                    ApiController.pokeApi.getPokemon(
                        evolveEntry.species.name
                    )
                }

                pokemons.add(

                    EvolvingPokemons(
                        from = BasicPokemon(
                            id= pokeFrom.id,
                            name = pokeFrom.name,
                            sprites = pokeFrom.sprites,
                            species = pokeFrom.species,
                            onClick = {viewModelScope.launch {
                                _actions.send(PokeScreenAction.pokemonClicked("pokemon/${pokeFrom.name}")) }
                            }
                        ),

                        to = BasicPokemon(
                            id= pokeTo.id,
                            name = pokeTo.name,
                            sprites = pokeTo.sprites,
                            species = pokeTo.species,
                            onClick = {viewModelScope.launch {
                                _actions.send(PokeScreenAction.pokemonClicked("pokemon/${pokeTo.name}")) }
                            }
                        ),
                        trigger = triggerText
                    )
                )

                species = evolves[0].species
                evolves = evolves[0].evolves_to
            }

        } catch (exception: Throwable) {

        }
        return pokemons
    }
}

class PokemonViewModelFactory(private val name: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
