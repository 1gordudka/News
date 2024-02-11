package com.igordudka.news.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igordudka.news.data.network.NewsService
import com.igordudka.news.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

enum class Status{
    OK, FAILED, LOADING, NOTHING
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsApi: NewsService
): ViewModel() {

    var newsList = MutableStateFlow<List<Article?>>(listOf())
    var responseStatus = MutableStateFlow<Status>(Status.NOTHING)

    fun getNews(q: String){
        viewModelScope.launch {
            responseStatus.value = Status.LOADING
            val response = newsApi.getNews(q)
            if (response.status == "error"){
                responseStatus.value = Status.FAILED
            }
            if (response.status == "ok"){
                responseStatus.value = Status.OK
                response.articles?.let {
                    newsList.value = it
                }
            }
        }
    }
}