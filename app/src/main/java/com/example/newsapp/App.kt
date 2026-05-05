package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.mapper.ArticleMapperData
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.data.source.remote.RemoteDataSourceImpl
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.api.NewsApiClient
import com.example.newsapp.domain.mapper.ArticleMapperDomain
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.usecase.GetCategoryTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SearchTopHeadlinesUseCase

class App : Application() {

    private val newsApi: NewsApi by lazy { NewsApiClient }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImpl(newsApi = newsApi)
    }
    private val articleMapperData by lazy { ArticleMapperData }
    private val articleMapperDomain by lazy { ArticleMapperDomain }
    private val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            mapper = articleMapperData
        )
    }

    val getCategoryTopHeadlinesUseCase: GetCategoryTopHeadlinesUseCase by lazy {
        GetCategoryTopHeadlinesUseCase(
            articleRepository = articleRepository,
            mapper = articleMapperDomain
        )
    }

    val searchTopHeadlinesUseCase: SearchTopHeadlinesUseCase by lazy {
        SearchTopHeadlinesUseCase(
            articleRepository = articleRepository,
            mapper = articleMapperDomain
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