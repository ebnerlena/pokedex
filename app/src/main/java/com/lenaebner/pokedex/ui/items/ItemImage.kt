package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.R
import com.lenaebner.pokedex.repository.item.ItemPreview

@Composable
fun ItemImage(item: ItemPreview, modifier: Modifier) {
    Column(modifier = modifier
        .padding(2.dp)) {
        CoilImage(
            data = item.sprite,
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
                .heightIn(min = 10.dp, max = 40.dp)
                .fillMaxWidth()
                .weight(2f)
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
        )
    }
}