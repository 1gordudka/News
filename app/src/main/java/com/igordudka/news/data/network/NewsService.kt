package com.igordudka.news.data.network

import com.igordudka.news.model.NewsResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

val API_KEY = "de36811cb0f849c6bd3d899f8cc17c4c"

interface NewsService {

    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("sortBy") s: String = "publishedAt",
        @Query("apiKey") key: String = API_KEY
    ) : NewsResponse
}