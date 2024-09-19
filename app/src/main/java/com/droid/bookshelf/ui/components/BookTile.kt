package com.droid.bookshelf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun BookTile(
    title: String,
    rating: Double,
    image: String,
    onLikedClick: (id: String, isLike: Boolean) -> Unit,
) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image Section
            SubcomposeAsyncImage(
                model = image,
                contentDescription = title,
                error = {},
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.width(100.dp)
            )

            // Info Section
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rating: $rating",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Like Button
                LikeButton(
                    isLiked = isLiked,
                    onClick = { isLiked = !isLiked; onLikedClick(title, isLiked) }
                )
            }
        }
    }
}

@Composable
@Preview
fun BookTitlePreview() {
    BookTile(
        title = "test",
        rating = 123.1,
        image = "https://cdn.myanimelist.net/images/anime/1600/134703.jpg"
    ) { _, _ ->

    }
}