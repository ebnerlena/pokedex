package com.lenaebner.pokedex.HomeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.home.SearchPokemomResult
import com.lenaebner.pokedex.ui.screenstates.SearchState



@Composable
fun Searchbar(onFocusChanged: (FocusState) -> Unit = {}, onQueryChanged: (String) -> Unit = {} ) {
    Column() {

        var query by remember { mutableStateOf("") }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){
            TextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                    //vm.onQueryChanged("$newValue%")
                    onQueryChanged("$newValue%")
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
    }
}


