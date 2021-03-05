package com.lenaebner.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.lenaebner.pokedex.ui.theme.PokedexTheme


@Preview
@Composable
fun HomePreview() {
    PokedexTheme {
        Home()
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { Home() }
        composable("pokedex") { Pokedex() }
        composable("pokemon/{pokemonName}",
            arguments = mutableStateListOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            SinglePokemonScreen(pokemonName = backStackEntry.arguments?.getString("pokemonName") )
        }
    }
}

@Composable
fun Home() {

    Scaffold(
        topBar = { AppBar() }
    ){
        Categories()
    }


}


@Composable
fun Categories(categories: List<String> = mutableStateListOf("Pokedex", "Moves", "Abilities", "Items","Locations", "Type Chars")) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CategoriesList(categories = categories, Modifier.weight(1f))

    }
}

@Composable
fun CategoriesList(categories: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        items(items = categories) { categorie ->
            Category(name = categorie, modifier = modifier)
            Spacer(modifier = Modifier.size(3.dp))
        }
    }
}

@Composable
fun Category(name: String, modifier: Modifier) {
    val navController = rememberNavController()
    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate("pokedex") {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h3,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(R.drawable.pokeball2),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .heightIn(min = 20.dp, max = 80.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .height(30.dp)
                    .width(30.dp),
            )
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h2
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
    )
}




