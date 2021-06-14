package com.lenaebner.pokedex.ItemsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.lenaebner.pokedex.R
import com.lenaebner.pokedex.repository.item.ItemPreview
import com.lenaebner.pokedex.ui.theme.transparentWhite

@Composable
fun ItemImage(item: ItemPreview, modifier: Modifier) {
    Column(modifier = modifier
        .padding(2.dp)) {

        val imageModifier = Modifier
            .heightIn(min = 10.dp, max = 40.dp)
            .fillMaxWidth()
            .weight(2f)
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)


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

        when (painter.loadState) {
            ImageLoadState.Empty, is ImageLoadState.Loading, is ImageLoadState.Error -> Image(
                painter = painterResource(id = R.drawable.pokeball2),
                modifier = imageModifier,
                contentDescription = "Fallback Image"
            )
        }
    }
}