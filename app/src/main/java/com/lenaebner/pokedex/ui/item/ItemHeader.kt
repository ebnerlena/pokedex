package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.lenaebner.pokedex.R
import com.lenaebner.pokedex.repository.item.Item

@Composable
fun ItemHeader(item: Item, imageRowModifier: Modifier) {
    Row(modifier = Modifier
        .padding(top=8.dp, start=16.dp)) {
        Text(
            text = item.category.toUpperCase(),
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold
        )
    }
    Row(
        modifier = imageRowModifier
    ) {

        val imageModifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
            .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)


        val painter = rememberCoilPainter(
            request = item.sprite,
            requestBuilder = {
                transformations(CircleCropTransformation())
            },
        )

        Image(
            modifier = imageModifier,
            painter = painter,
            contentDescription = "Item Image",
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center
        )
    }
}