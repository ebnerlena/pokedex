package com.lenaebner.pokedex

import PokedexTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint


val ActiveNavController = compositionLocalOf<NavController> {error("No navcontroller found") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // Greeting("Android")
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name",
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(8.dp)
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexTheme {
        Greeting("Android")
    }
}