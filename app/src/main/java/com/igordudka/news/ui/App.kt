package com.igordudka.news.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.igordudka.news.viewmodel.MainViewModel

val newsScreen = 0
val detailScreen = 1

@Composable
fun App(
    mainViewModel: MainViewModel = hiltViewModel()
) {

    var screen by remember {
        mutableIntStateOf(newsScreen)
    }
    var currentDetail by remember {
        mutableIntStateOf(-1)
    }
    val news by mainViewModel.newsList.collectAsState()
    val status by mainViewModel.responseStatus.collectAsState()
    val listState = rememberLazyListState()



    when(screen){
        newsScreen -> NewsScreen(news = news, goToDetail = {
            currentDetail = it
            screen = detailScreen
        }, status = status, search = {
            mainViewModel.getNews(it)
        }, listState)
        detailScreen -> news[currentDetail]?.let {
            DetailScreen(article = it) {
                screen = newsScreen
            }
        }
    }

}