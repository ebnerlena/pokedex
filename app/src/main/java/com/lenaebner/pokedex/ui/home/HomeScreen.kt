package com.lenaebner.pokedex.HomeScreen

import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.viewmodels.SearchViewModel


@Composable
fun Home(vm: SearchViewModel) {

    val isSearching = remember { mutableStateOf(false)}

    val paddingFromBase by animateDpAsState(
        targetValue = when(isSearching.value) {
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
                    style = if(isSearching.value) MaterialTheme.typography.body2 else MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(8.dp)
                        .paddingFromBaseline(paddingFromBase)
                        .animateContentSize()
                )
            }
        }
    ){

        Column() {
            Searchbar(vm = vm, onFocusChanged = { focusState ->  isSearching.value = focusState == FocusState.Active })
            Spacer(modifier = Modifier.height(8.dp))
            Categories()
        }
    }
}

