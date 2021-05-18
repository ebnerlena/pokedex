package com.lenaebner.pokedex.ItemScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.lenaebner.pokedex.api.models.Item
import com.lenaebner.pokedex.R

@Composable
fun ItemHeader(item: Item, imageRowModifier: Modifier) {
    Row(modifier = Modifier
        .padding(top=8.dp, start=16.dp)) {
        Text(
            text = item.category.name.toUpperCase(),
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
        CoilImage(
            data = item.sprites.default,
            contentDescription = "Item",
            loading = {
                Image(
                    painter = painterResource(id = R.drawable.pokemon3),
                    contentDescription = "Fallback Image"
                )
            },
            requestBuilder = {
                transformations(CircleCropTransformation())
            },modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
        )
    }
}