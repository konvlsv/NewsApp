package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.mapper.ArticleMapper
import com.example.newsapp.data.repository.ArticleRepository
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.data.source.remote.RemoteDataSourceImpl
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.api.NewsApiClient

class App: Application() {

    private val newsApi: NewsApi by lazy { NewsApiClient }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImpl(newsApi = newsApi)
    }
    private val articleMapper by lazy { ArticleMapper }
    val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            mapper = articleMapper
        )
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}