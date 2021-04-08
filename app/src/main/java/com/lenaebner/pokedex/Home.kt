package com.lenaebner.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.lenaebner.pokedex.shared.Navigation
import com.lenaebner.pokedex.ui.theme.PokedexTheme

//use for deployment
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun TestPreview(){

    PokedexTheme() {
        Navigation()
    }
}

//just for visual preview - cause nav not working like that
@Preview
@Composable
fun HomePreview() {
    PokedexTheme {
        Home(navController = rememberNavController() )
    }
}

@Preview
@Composable
fun Hom2ePreview() {
    PokedexTheme {
        Searchbar(navController = rememberNavController() )
    }
}

@Composable
fun Home(navController: NavController) {

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
            Searchbar(navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
            Categories(navController = navController)
        }
    }
}


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

@Composable
fun Categories(
    navController: NavController,
    categories: List<String> = mutableStateListOf("Pokedex", "Moves", "Generations", "Items")) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CategoriesList(navController = navController, categories = categories, Modifier.weight(1f))
    }
}

@Composable
fun CategoriesList(navController: NavController, categories: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        items(items = categories) { categorie ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Category(name = categorie, modifier = modifier, navController = navController)
                Spacer(modifier = Modifier.size(3.dp))
            }

        }
    }
}

@Composable
fun Category(name: String, modifier: Modifier, navController: NavController) {

    Card(
        elevation = 2.dp,
        modifier = modifier.padding(horizontal = 32.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate(name.toLowerCase())
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h4,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(3f),
                textAlign = TextAlign.Start
            )
            Image(
                painter = painterResource(R.drawable.pokeball2),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .heightIn(min = 20.dp, max = 70.dp)
                    .padding(8.dp)
                    .weight(1f)
            )
        }
    }
}

