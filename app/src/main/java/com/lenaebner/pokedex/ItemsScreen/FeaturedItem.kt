package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.lenaebner.pokedex.ActiveNavController
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite
import com.lenaebner.pokedex.R


@Composable
fun FeaturedItem(
    item: Item,
    modifier: Modifier = Modifier,
) {
    val navController = ActiveNavController.current

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
            ItemCardHeader(
                item = item,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .align(Alignment.End)
                    .padding(bottom = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ItemCosts(
                    item = item,
                    modifier = Modifier
                        .weight(2f)
                        .align(Alignment.Top)
                )
                ItemImage(
                    item = item,
                    modifier = Modifier
                        .weight(3f)
                )
            }
        }
    }
}
