package com.lenaebner.pokedex.HomeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.repository.pokemon.SearchPokemonPreview
import com.lenaebner.pokedex.ui.screenstates.SearchState
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.viewmodels.SearchViewModel


@Composable
fun Searchbar(vm: SearchViewModel, onFocusChanged: (FocusState) -> Unit = {}) {
    Column() {

        var query = remember { mutableStateOf("") }
        val results = vm.results.collectAsState().value


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            TextField(
                value = query.value,
                onValueChange = { newValue ->
                    query.value = newValue
                    vm.onQueryChanged("$newValue%")
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant
                ),
                label = { Text(
                    text = "Search",
                    color = MaterialTheme.colors.secondaryVariant
                ) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.background
                    )
                    .onFocusChanged{
                        onFocusChanged(it)
                    }
            )
        }

        when(results) {
            is SearchState.Content ->  LazyColumn(
                Modifier
                    .padding(8.dp)
                    .animateContentSize()) {
                    items(results.pokemons) {
                        SearchPokemomResult(it)
                    }
            }
        }
    }
}

@Composable
fun SearchPokemomResult(pokemon: SearchPokemonPreview) {
    val navController = ActiveNavController.current

    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("pokemon/${pokemon.id}?speciesId=${pokemon.speciesId}")
            }
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "#${pokemon.id}   ${pokemon.name.capitalize()}",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp),
            textAlign = TextAlign.Start
        )
    }

    Divider(
        Modifier
            .height(2.dp)
            .padding(horizontal = 16.dp),
        color = transparentGrey
    )
}
