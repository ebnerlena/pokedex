package com.lenaebner.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.api.models.ItemPreview
import com.lenaebner.pokedex.api.models.Pokemon
import com.lenaebner.pokedex.api.models.PokemonWithColor
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Items (navController: NavController) {

    Scaffold (
        topBar = {
            Header(navController = navController,
                textColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = Color.White,
                title = "Items",
                iconTint = MaterialTheme.colors.secondaryVariant,
                icon = Icons.Default.ArrowBack)
        },
        content = { ItemsGrid(navController = navController) }
    )
}

@Composable
fun fetchItems(offset: Int, limit: Int) : MutableState<List<Item>> {

    val scope = rememberCoroutineScope()
    var items: MutableState<List<Item>> = mutableStateOf(emptyList())


    val task = scope.launch {
        val list = withContext(Dispatchers.IO) {
            ApiController.pokeApi.getItems(offset = offset,limit = limit)
        }

        items.value = list.results.map {
            ApiController.pokeApi.getItem(it.name)
        }
    }
    return items
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsGrid(navController: NavController) {

    var items: MutableState<List<Item>> = fetchItems(offset = 0, limit = 20)


    LazyVerticalGrid(
        cells = GridCells.Fixed(2), modifier = Modifier.padding(4.dp)
    ) {
        items(items.value) { item ->
            FeaturedItem(
                item = item,
                navController = navController,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun FeaturedItem(
    item: Item,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    Card(
        elevation = 2.dp,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = transparentGrey
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("item/${item.name}") {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
        ) {
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.name.toString().capitalize(),
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    modifier = Modifier
                        .weight(3f)
                        .padding(top = 6.dp, start = 12.dp)
                )
                Text(
                    text = '#'+item.id.toString(),
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 6.dp)
                )
            }
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.End)
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp, end = 4.dp, top = 16.dp)
                    .align(
                        Alignment.Top
                    )) {
                    Text(
                        text = item.cost.toString()+" Cost",
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 6.dp)
                    )
                }
                Column(modifier = Modifier
                    .weight(3f)
                    .padding(2.dp)) {
                    CoilImage(
                        data = item.sprites.default,
                        contentDescription = "Item",
                        loading = {
                            Image(
                                painter = painterResource(id = R.drawable.pokemon1),
                                contentDescription = "Fallback Image"
                            )
                        },
                        requestBuilder = {
                            transformations(CircleCropTransformation())
                        },
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .heightIn(min = 20.dp, max = 80.dp)
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                            .background(transparentWhite)
                    )
                }
            }
        }
    }
}
