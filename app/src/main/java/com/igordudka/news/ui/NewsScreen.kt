package com.igordudka.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.news.model.Article
import com.igordudka.news.viewmodel.MainViewModel
import com.igordudka.news.viewmodel.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    news: List<Article?>,
    goToDetail: (Int) -> Unit,
    status: Status,
    search: (String) -> Unit,
    listState: LazyListState
) {

    var searchText by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Screen {
        BigTitleText(text = "Новости")
        TextField(value = searchText, onValueChange = { searchText = it },
            keyboardActions = KeyboardActions(onDone = {search(searchText)
            keyboardController?.hide()}),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                ),
            colors = TextFieldDefaults.colors(
                cursorColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = tileColor,
                unfocusedContainerColor = tileColor,
                unfocusedIndicatorColor = Color.Black.copy(alpha = 0f),
                focusedIndicatorColor = Color.Black.copy(alpha = 0f),
                focusedTextColor = Color.White,

            ),
            placeholder = {
                Text(text = "Поиск новостей", color = Color.White.copy(alpha = 0.54f), fontSize = 17.sp)
            },
            textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
        )
        when(status){
            Status.NOTHING -> {}
            Status.OK -> {
                LazyColumn(state = listState){
                    items(news){article->
                        if (article?.title != null && article?.description != null && article.publishedAt != null){
                            NewsTile(title = article.title, description = article.description,
                                date = article.publishedAt.slice(0..9) + " " + article.publishedAt.slice(11..15)) {
                                goToDetail(news.indexOf(article))
                            }
                        }
                    }
                }
            }
            Status.LOADING -> {
                MediumTitleText(text = "Загрузка...")
            }
            Status.FAILED -> {
                MediumTitleText(text = "Ошибка!")
            }
        }
    }
}

@Composable
fun NewsTile(
    title: String,
    description: String,
    date: String,
    onClick: () -> Unit
) {

    Column(
        Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(21.dp))
            .background(tileColor)
            .clickable { onClick() }
            .padding(16.dp),) {
        MediumTitleText(text = title, shortLength = true)
        SmallText(text = description, shortLength = true)
        DateText(text = date)
    }
}