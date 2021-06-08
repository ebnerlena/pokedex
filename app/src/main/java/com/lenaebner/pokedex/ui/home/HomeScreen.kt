package com.lenaebner.pokedex.HomeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.home.SearchPokemomResult
import com.lenaebner.pokedex.ui.home.SearchResults
import com.lenaebner.pokedex.ui.viewmodels.SearchViewModel


@Composable
fun Home(vm: SearchViewModel) {

    var isSearching by remember { mutableStateOf(false)}
    val results = vm.results.collectAsState().value

    val paddingFromBase by animateDpAsState(
        targetValue = when(isSearching) {
            true -> 8.dp
            false -> 32.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy
        )
    )

    Scaffold(
        topBar = {
            Row {
                Text(
                    text = "What Pokemon are you looking for?",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = if(isSearching) MaterialTheme.typography.body2 else MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(8.dp)
                        .paddingFromBaseline(paddingFromBase)
                        .animateContentSize()
                )
            }
        }
    ){

        Column() {
            Searchbar(
                onFocusChanged = { focusState ->  isSearching = focusState == FocusState.Active },
                onQueryChanged = { newValue -> vm.onQueryChanged("$newValue%") }
            )
            SearchResults(results = results)
            Spacer(modifier = Modifier.height(8.dp))
            Categories()
        }
    }
}

