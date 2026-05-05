package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.data.source.remote.RemoteDataSourceImpl
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.api.NewsApiClient
import com.example.newsapp.domain.mapper.DomainModelsMapper
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.usecase.GetCategoryTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchTopHeadlinesUseCase
import com.example.newsapp.ui.mapper.DisplayModelsMapper

class App : Application() {

    private val newsApi: NewsApi by lazy { NewsApiClient }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImpl(newsApi = newsApi)
    }
    private val dataModelsMapper by lazy { DataModelsMapper }
    private val domainModelsMapper by lazy { DomainModelsMapper }
    val displayModelsMapper by lazy { DisplayModelsMapper }
    private val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            mapper = dataModelsMapper
        )
    }

    val getCategoryTopHeadlinesUseCase: GetCategoryTopHeadlinesUseCase by lazy {
        GetCategoryTopHeadlinesUseCase(
            articleRepository = articleRepository,
            mapper = domainModelsMapper
        )
    }

    val searchTopHeadlinesUseCase: SearchTopHeadlinesUseCase by lazy {
        SearchTopHeadlinesUseCase(
            articleRepository = articleRepository,
            mapper = domainModelsMapper
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