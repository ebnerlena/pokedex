package com.lenaebner.pokedex

import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.lenaebner.pokedex.ui.theme.PokedexTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface {
                MyApp()
            }

        }
    }
}

@Composable
fun MyApp() {

    PokedexTheme {
        Navigation()
    }
}
