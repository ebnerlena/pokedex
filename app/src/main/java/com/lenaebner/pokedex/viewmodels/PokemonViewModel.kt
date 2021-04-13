package com.lenaebner.pokedex.viewmodels


import androidx.lifecycle.*
import com.lenaebner.pokedex.ApiController
import com.lenaebner.pokedex.ScreenStates.PokemonScreenState
import com.lenaebner.pokedex.api.models.EvolutionChainDetails
import com.lenaebner.pokedex.api.models.EvolvingPokemons
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonSpecies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _uiState = MutableLiveData<PokemonScreenState>()
    val uiState : LiveData<PokemonScreenState> = _uiState

    private var _pokemon: Pokemon = Pokemon()
    private var _species: PokemonSpecies = PokemonSpecies()

    init {
        val pokeName: String = savedStateHandle["pokemonName"] ?: "pikachu"
        fetchPokemon(pokeName)
    }

    private fun createContentState(pokemon: Pokemon, species: PokemonSpecies) {
        _pokemon = pokemon
        _species = species

        _uiState.postValue(
            PokemonScreenState.Content(
                pokemon = pokemon,
                species = species,
            )
        )
    }

    private fun updateEvolutionChainState(entries: MutableList<EvolvingPokemons>) {

        _uiState.postValue(
            PokemonScreenState.Content(
                pokemon = _pokemon,
                species = _species,
                evolutionChainPokemons = entries
            )
        )
    }

    private fun fetchPokemon(name: String) {

        _uiState.postValue(
            PokemonScreenState.Loading
        )

        viewModelScope.launch {

            try {
                val pokemon = withContext(Dispatchers.IO){ ApiController.pokeApi.getPokemon(name)}
                val species = withContext(Dispatchers.IO){  ApiController.pokeApi.getPokemonSpecies(pokemon.id)}
                val id = species.evolution_chain.url.split("/")[6].toInt()
                val chain = withContext(Dispatchers.IO){  ApiController.pokeApi.getEvolutionChain(id)}

                //update ui
                createContentState(pokemon = pokemon, species=species)

                //fetch pokemons for Evolution Chain seperatly
                fetchEvolutionChainPokemons(evolutionChainDetails = chain)

            } catch( exception: Throwable) {
                _uiState.postValue(
                    PokemonScreenState.Error(
                        message = exception.message ?: "An error occured when fetching the pokemon",
                        retry = { fetchPokemon(name) }
                    )
                )
            }

        }
    }

    private fun fetchEvolutionChainPokemons(evolutionChainDetails: EvolutionChainDetails) {

        val pokemons: MutableList<EvolvingPokemons> = mutableListOf()
        var evolves = evolutionChainDetails.chain.evolves_to
        var species = evolutionChainDetails.chain.species

        viewModelScope.launch {
            try {
                while(evolves.isNotEmpty()) {
                    val evolveEntry = evolves[0]
                    val details = evolveEntry.evolution_details[0]
                    val triggerText = when (details.trigger.name) {
                        "level-up" -> if (details.min_happiness != null) "Hpy: " + details.min_happiness else "Lvl: " + evolveEntry.evolution_details[0].min_level
                        "use-item" -> details.item?.name?.capitalize() ?: "Item"
                        else -> "Level up"
                    }

                    pokemons.add(
                        EvolvingPokemons(
                            from = withContext(Dispatchers.IO){ ApiController.pokeApi.getPokemon(species?.name.orEmpty())},
                            to = withContext(Dispatchers.IO){ ApiController.pokeApi.getPokemon(evolveEntry.species.name)},
                            trigger = triggerText
                        )
                    )

                    species = evolves[0].species
                    evolves = evolves[0].evolves_to
                }
                updateEvolutionChainState(entries = pokemons)

            } catch (exception: Throwable) {
                _uiState.postValue(
                    PokemonScreenState.Error(
                        message = "An error occured when fetching the pokemons for EvolutionChain",
                        retry = {fetchEvolutionChainPokemons(evolutionChainDetails)}
                    )
                )
            }
        }
    }

}