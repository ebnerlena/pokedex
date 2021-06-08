package com.lenaebner.pokedex.SinglePokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.lenaebner.pokedex.R
import com.lenaebner.pokedex.repository.pokemon.EvolvingPokemons
import com.lenaebner.pokedex.repository.pokemon.UiBasicPokemon
import com.lenaebner.pokedex.ui.theme.transparentGrey
import com.lenaebner.pokedex.ui.theme.transparentWhite

@Composable
fun EvolutionChain(evolutionChainEntries: List<EvolvingPokemons>) {

    LazyColumn {
        item {
            Text(text = "Evolution Chain",
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 16.dp)
            )
        }

        itemsIndexed(evolutionChainEntries) {idx, entry->
            EvolutionEntry(evolveEntry = entry)

            if(idx < evolutionChainEntries.size-1) {
                Divider(modifier = Modifier
                    .height(2.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(), color = transparentGrey
                )
            }
        }
    }
}


@Composable
fun EvolutionEntry(evolveEntry: EvolvingPokemons) {

    Row(modifier = Modifier.padding(16.dp)) {
        PokemonEvlove(
            pokemon = evolveEntry.from,
            modifier = Modifier
                .weight(1f),
        )
        Trigger(
            triggerText = evolveEntry.trigger,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .fillMaxHeight()
                .weight(1f)
        )

        PokemonEvlove(
            pokemon = evolveEntry.to,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun PokemonEvlove(pokemon: UiBasicPokemon, modifier: Modifier) {

    Column(modifier = modifier
        .clickable(onClick = pokemon.onClick)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            val painter = rememberCoilPainter(
                request = pokemon.sprite,
                previewPlaceholder = R.drawable.pokemon1
            )

            Image(
                modifier = Modifier
                        .width(60.dp),
                painter = painter,
                contentDescription = "Evolving Pokemon Image",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )

            when (painter.loadState) {
                ImageLoadState.Empty, is ImageLoadState.Loading, is ImageLoadState.Error -> Image(
                    painter = painterResource(id = R.drawable.pokemon1),
                    contentDescription = "Fallback Image"
                )
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val size = if(maxWidth > 150.dp) 16.sp else 12.sp
                Text(
                    text = pokemon.species.capitalize(),
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(8.dp).fillMaxWidth(),
                    fontSize = size,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun Trigger(triggerText: String, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = transparentGrey
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {

            Text(
                text = triggerText,
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
