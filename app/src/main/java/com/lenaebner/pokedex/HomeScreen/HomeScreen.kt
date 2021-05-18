package com.lenaebner.pokedex.HomeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.ui.theme.PokedexTheme


//just for visual preview - cause nav not working like that
@Preview
@Composable
fun HomePreview() {
    PokedexTheme {
        Home()
    }
}

@Preview
@Composable
fun SearchbarPreview() {
    PokedexTheme {
        Searchbar()
    }
}

@Composable
fun Home() {
    Scaffold(
        topBar = {
            Row {
                Text(
                    text = "What Pokemon are you looking for?",
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(8.dp)
                        .paddingFromBaseline(32.dp)
                )
            }
        }
    ){

        Column() {
            Searchbar()
            Spacer(modifier = Modifier.height(8.dp))
            Categories()
        }
    }
}