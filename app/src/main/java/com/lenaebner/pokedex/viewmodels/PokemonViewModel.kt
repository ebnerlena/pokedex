package com.lenaebner.pokedex.viewmodels


import android.util.Log
import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.PokemonScreenState
import com.lenaebner.pokedex.api.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class PokeScreenAction {
    object NavigateBack : PokeScreenAction()
    data class pokemonClicked(val destination: String) : PokeScreenAction()
}

class PokemonViewModel(private val name: String) : ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()

    private val _pokemonFlow = flow {
        emit(fetchPokemon(name))
    }

    private val _speciesFlow = flow {
        emit(fetchSpecies())
    }

    private val _evolutionChainFlow = flow {
        emit(fetchEvolutionChain())
    }

    private val _pokeComplete = combine(
        _pokemonFlow,
        _speciesFlow,
        _evolutionChainFlow,
        transform = { pokemon, species, evolutions ->
            PokemonSpeciesChain(pokemon, species, evolutions)
        }
    )

    private val _actions = Channel<PokeScreenAction>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    private val _uiState = flow {
        emit(PokemonScreenState.Loading)
        try {
            _pokeComplete.collect {
                emit(createContentState(it))
            }
        } catch (ex: Throwable) {
            emit(
                PokemonScreenState.Error(
                    message = "An error occured when fetching the requested pokemon",
                    retry = {})
            )
        }
    }

    val uiState = _uiState.asLiveData()


    private fun navigate(destination: String) {
        viewModelScope.launch {
            _actions.send(PokeScreenAction.pokemonClicked(destination = destination))
        }
    }

    private fun createContentState(pokemonComplete: PokemonSpeciesChain) =
        PokemonScreenState.Content(
            pokemon = pokemonComplete.pokemon,
            species = pokemonComplete.species,
            evolutionChainPokemons = pokemonComplete.evolutionChainPokemons,
            backClicked = { viewModelScope.launch { _actions.send(PokeScreenAction.NavigateBack) } },
            pokemonClicked = {}
        )

    private suspend fun fetchSpecies(): PokemonSpecies {

        Log.d("foo", "in fetch species with: " + _pokemon.value?.id.toString())

        return withContext(Dispatchers.IO) {
            ApiController.pokeApi.getPokemonSpecies(
                _pokemon.value?.id ?: 13
            )
        }
    }


    private suspend fun fetchEvolutionChain(): MutableList<EvolvingPokemons> {
        val id = 25 //_species.evolution_chain.url.split("/")[6].toInt()
        val evolutionChain =
            withContext(Dispatchers.IO) { ApiController.pokeApi.getEvolutionChain(id) }

        //fetch pokemons for Evolution Chain seperatly
        return fetchEvolutionChainPokemons(evolutionChainDetails = evolutionChain)
    }

    private suspend fun fetchPokemon(name: String): Pokemon {
        Log.d("foo", "in fetch pokemon with: " + name)
        return withContext(Dispatchers.IO) { ApiController.pokeApi.getPokemon(name) }
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

                pokemons.add(
                    EvolvingPokemons(
                        from = withContext(Dispatchers.IO) {
                            ApiController.pokeApi.getPokemon(
                                species?.name.orEmpty()
                            )
                        },
                        to = withContext(Dispatchers.IO) {
                            ApiController.pokeApi.getPokemon(
                                evolveEntry.species.name
                            )
                        },
                        trigger = triggerText
                    )
                )

                species = evolves[0].species
                evolves = evolves[0].evolves_to
            }

            //updateEvolutionChainState(entries = pokemons)

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
