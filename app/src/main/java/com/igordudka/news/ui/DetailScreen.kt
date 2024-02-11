package com.igordudka.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.igordudka.news.model.Article

@Composable
fun DetailScreen(
    article: Article,
    goBack: () -> Unit
) {

    Screen {
        IconButton(onClick = goBack) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null,
                Modifier.size(35.dp), tint = Color.White)
        }
        LazyColumn{
            item {
                if(article.title != null){
                MediumTitleText(text = article.title)
                }
                if(article.urlToImage != null){
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        AsyncImage(model = article.urlToImage, contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.FillBounds)
                    }
                }
                if (article.author != null){
                    SmallText(text = "Автор: ${article.author}")
                }
                if (article.publishedAt != null){
                    DateText(text = article.publishedAt.slice(0..9) + " " + article.publishedAt.slice(11..15))
                }
                if (article.url != null){
                    LinkText(uri = article.url)
                }
                if (article.description != null){
                    MediumText(text = article.description)
                } }
        }
    }
}