package com.droid.bookshelf

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droid.bookshelf.data.BookShelfRepositoryImpl
import com.droid.bookshelf.ui.screens.HomeScreen
import com.droid.bookshelf.ui.screens.LoginScreen
import com.droid.bookshelf.ui.screens.SignUpScreen
import com.droid.bookshelf.ui.theme.BookShelfTheme
import com.droid.bookshelf.ui.viewmodels.BookShelfViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: BookShelfViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.SignUp
                    ) {
                        composable<Screens.SignUp> {
                            val flow by remember {
                                mutableStateOf(viewModel.getCountries())
                            }
                            Box(modifier = Modifier.padding(innerPadding)) {
                                SignUpScreen(flow) {
                                    navController.navigate(Screens.Home) {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                        composable<Screens.Login> {
                            Box(modifier = Modifier.padding(innerPadding)) {
                                LoginScreen()
                            }
                        }
                        composable<Screens.Home> {
                            val flow by remember {
                                mutableStateOf(viewModel.getBooks())
                            }
                            Box(modifier = Modifier.padding(innerPadding)) {
                                HomeScreen(flow)
                            }
                        }
                    }
                }

            }
        }
    }
}

object Screens {
    @Serializable
    object SignUp

    @Serializable
    object Login

    @Serializable
    object Home
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookShelfTheme {
        Greeting("Android")
    }
}