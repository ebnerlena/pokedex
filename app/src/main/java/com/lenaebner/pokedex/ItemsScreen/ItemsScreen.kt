package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.ItemScreen.ItemsGrid
import com.lenaebner.pokedex.ScreenStates.ItemsOverviewScreenState
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.viewmodels.ItemsViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun Items(items: List<ItemsViewModel.ItemOverview>) {

    Scaffold (
        topBar = {
            Header(
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Items",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { ItemsGrid(items = items) }
    )
}

@Composable
fun ItemsScreen(vm: ItemsViewModel) {

    val uiState = vm.uiState.observeAsState(initial = ItemsOverviewScreenState.Loading).value
    ItemsScreen(state = uiState)

    val navController = ActiveNavController.current
    LaunchedEffect(key1 = "items actions") {
        vm.actions.collect {
            when(it) {
                is ItemsViewModel.ItemsAction.Navigate -> navController.navigate(it.destination)
            }
        }
    }
}

@Composable
fun ItemsScreen(state: ItemsOverviewScreenState) {

    when(state) {
        is ItemsOverviewScreenState.Content -> Items(
            items = state.items
        )
        is ItemsOverviewScreenState.Loading -> loadingSpinner()
        is ItemsOverviewScreenState.Error -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            ErrorScreen(errorMessage = state.message, retry = state.retry)
        }
    }
}