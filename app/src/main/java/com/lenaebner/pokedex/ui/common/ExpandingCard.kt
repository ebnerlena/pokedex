package com.lenaebner.pokedex.shared

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import com.lenaebner.pokedex.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ExpandingCard(title: String, content: @Composable () -> Unit) {

    var expanded = remember { mutableStateOf(false) }
    Card (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), backgroundColor = Color.White, shape = MaterialTheme.shapes.medium) {
        Column(
            Modifier
                .animateContentSize()
                .fillMaxWidth()
        ) {
            Text(text = title,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(8.dp))
            if (expanded.value) {

                content()
                IconButton(onClick = { expanded.value = false }) {
                    // Icon(painter = painterResource(id = R.drawable.arrow_drop_up), contentDescription = null)
                }

            } else {
                IconButton(onClick = { expanded.value = true }) {
                    // Icon(painter = painterResource(id = R.drawable.arrow_drop_down), contentDescription = null)
                }
            }
        }
    }
}