package com.lenaebner.pokedex

import android.os.Bundle
import android.util.Log

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.lenaebner.pokedex.HomeScreen.Home
import com.lenaebner.pokedex.api.PokeApi
import com.lenaebner.pokedex.shared.Navigation
import com.lenaebner.pokedex.ui.theme.PokedexTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val ActiveNavController = compositionLocalOf<NavController> {error("No navcontroller found") }


class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MyApp() {

    PokedexTheme {
        Navigation()
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun AppPreview(){

    PokedexTheme() {
        Navigation()
    }
}
