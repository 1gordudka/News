package com.igordudka.news.di

import com.igordudka.news.data.network.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNewsApi() : NewsService{

        val api: NewsService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(NewsService::class.java)
        }
        return api
    }

}