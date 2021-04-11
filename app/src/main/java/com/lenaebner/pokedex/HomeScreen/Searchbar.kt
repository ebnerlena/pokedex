package com.lenaebner.pokedex.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun Searchbar(navController: NavController) {
    Column() {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            OutlinedTextField(
                value = "query", onValueChange = { /*TODO*/ }, label = { Text(text = "Search") },
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.onSecondary
                    )
            )
        }

        LazyColumn {
            /* itemsIndexed(
                 items = items retrieved from query
                 https://morioh.com/p/74d132a326d7
                 https://github.com/mitchtabian/MVVMRecipeApp/tree/searchview-toolbar
             ){index, recipe ->
                 RecipeCard(recipe = recipe, onClick = {})
             } */
        }
    }
}
