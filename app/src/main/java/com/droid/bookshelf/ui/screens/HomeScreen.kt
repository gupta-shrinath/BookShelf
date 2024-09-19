package com.droid.bookshelf.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.dp
import com.droid.bookshelf.data.models.Book
import com.droid.bookshelf.ui.components.BookTile
import com.droid.bookshelf.ui.components.YearTabs
import com.droid.bookshelf.ui.viewmodels.Async
import com.droid.bookshelf.utils.getYearFromTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(books: Flow<Async<List<Book>>>) {
    val books by books.collectAsState(initial = Async.Loading)
    when (books) {
        is Async.Error -> {
            Text(text = "Error getting books")
        }

        is Async.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Loading Books")
                }
            }


        }

        is Async.Success -> {
            val books = (books as Async.Success).data
            val years by remember {
                mutableStateOf(books.map { it.publishedChapterDate.getYearFromTimestamp() }
                    .distinct())
            }
            var selectedYear by remember {
                mutableIntStateOf(years.first())
            }
            val state = rememberLazyListState()
            val scope = rememberCoroutineScope()
            val firstVisibleItemIndex  by remember { derivedStateOf { state.firstVisibleItemIndex } }
            val firstVisibleItemScrollOffset by remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
            Column {
                YearTabs(years = years, selectedYear = selectedYear) {
                    selectedYear = it
                    scope.launch {
                        state.scrollToItem(books.indexOfFirst { it.publishedChapterDate.getYearFromTimestamp() == selectedYear })

                    }
                }
                LaunchedEffect(firstVisibleItemIndex,firstVisibleItemScrollOffset) {
                    Log.d("TAG", "HomeScreen: Scrolled ${state.firstVisibleItemIndex}")
                    selectedYear = books.get(state.firstVisibleItemIndex).publishedChapterDate.getYearFromTimestamp()
                    Log.d("TAG", "HomeScreen: Year to ${selectedYear}")

                }
                LazyColumn(
                    state = state
                ) {
                    items(books) {
                        BookTile(title = it.title, rating = it.score, image = it.image) { _, _ ->
                        }
                    }
                }
            }

        }
    }
}