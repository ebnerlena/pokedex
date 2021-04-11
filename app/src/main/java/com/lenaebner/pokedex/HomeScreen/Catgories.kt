package com.lenaebner.pokedex.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lenaebner.pokedex.R
import androidx.navigation.NavController
import androidx.navigation.compose.navigate

@Composable
fun Categories(
    navController: NavController,
    categories: List<String> = mutableStateListOf("Pokedex", "Moves", "Generations", "Items")
) {
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