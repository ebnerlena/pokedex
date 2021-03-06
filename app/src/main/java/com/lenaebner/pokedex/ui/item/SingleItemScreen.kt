package com.lenaebner.pokedex

import PokedexTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ItemScreen.ItemCard
import com.lenaebner.pokedex.ItemScreen.ItemHeader
import com.lenaebner.pokedex.ScreenStates.ItemScreenState
import com.lenaebner.pokedex.repository.item.Item
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.LoadingSpinner
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.viewmodels.ItemViewModel
import kotlinx.coroutines.flow.collect


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun ItemPreview() {
    ItemScreen(state = ItemScreenState.Loading)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleItem(item: Item, backClicked: () -> Unit) {

    PokedexTheme {

        Scaffold (
            topBar = {
                Header(
                    textColor = Color.White,
                    backgroundColor = transparentGrey,
                    title = item.name,
                    icon = Icons.Default.ArrowBack,
                    backClicked = backClicked
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(transparentGrey)
                ) {
                    ItemHeader(
                        item = item,
                        imageRowModifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    ItemCard(item = item, modifier = Modifier.weight(3f) )
                }
            }
        )
    }
}

@Composable
fun ItemScreen(vm: ItemViewModel) {

    
    val uiState = vm.uiState.collectAsState(initial = ItemScreenState.Loading).value
   ItemScreen(state = uiState)

    val navController = ActiveNavController.current

     LaunchedEffect(vm.actions) {
        vm.actions.collect {
            when(it) {
                is ItemViewModel.ItemScreenAction.NavigateBack -> navController.navigateUp()
            }
        }
    }
}

@Composable
fun ItemScreen(state: ItemScreenState) {
    when(state) {
        is ItemScreenState.Content -> SingleItem(
            item = state.item,
            backClicked = state.backClicked
        )
        is ItemScreenState.Loading -> LoadingSpinner()
        is ItemScreenState.Error ->  Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            ErrorScreen(errorMessage = state.message, retry = state.retry)
        }

    }
}