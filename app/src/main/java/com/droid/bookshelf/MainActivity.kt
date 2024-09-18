package com.droid.bookshelf

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.droid.bookshelf.data.BookShelfRepositoryImpl
import com.droid.bookshelf.ui.screens.SignUpScreen
import com.droid.bookshelf.ui.theme.BookShelfTheme
import com.droid.bookshelf.ui.viewmodels.BookShelfViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: BookShelfViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val application = LocalContext.current.applicationContext as Application
                    val flow by remember {
                        mutableStateOf(viewModel.getCountries(application))
                    }
                    SignUpScreen(flow)
                }
            }
        }
    }
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