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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lenaebner.pokedex.ItemScreen.ItemsGrid
import com.lenaebner.pokedex.ScreenStates.ItemsOverviewScreenState
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.shared.ErrorScreen
import com.lenaebner.pokedex.shared.Header
import com.lenaebner.pokedex.shared.loadingSpinner
import com.lenaebner.pokedex.viewmodels.ItemsViewModel

@Composable
fun Items(items: MutableList<Item>, navController: NavController) {

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Items",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { ItemsGrid(items = items, navController = navController) }
    )
}

@Composable
fun ItemsScreen(navController: NavController) {

    val vm: ItemsViewModel = viewModel()

    val uiState = vm.uiState.observeAsState(initial = ItemsOverviewScreenState.Loading).value
   ItemsScreen(state = uiState, navController = navController)
}

@Composable
fun ItemsScreen(state: ItemsOverviewScreenState, navController: NavController) {

    when(state) {
        is ItemsOverviewScreenState.Content -> Items(
            items = state.items,
            navController = navController
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