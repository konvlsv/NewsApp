package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.data.source.local.LocalDataSource
import com.example.newsapp.data.source.local.LocalDataSourceImpl
import com.example.newsapp.data.source.local.db.ArticleDao
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.data.source.remote.RemoteDataSourceImpl
import com.example.newsapp.data.source.remote.api.NewsApi
import com.example.newsapp.data.source.remote.api.RetrofitClient
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.usecase.GetDetailArticleUseCase
import com.example.newsapp.domain.usecase.GetTopHeadlinesUseCase
import com.example.newsapp.domain.usecase.SaveDetailArticleUseCase
import com.example.newsapp.ui.common.mapper.UiModelsMapper
import com.example.newsapp.domain.navigation.BrowserNavigator
import com.example.newsapp.data.navigation.BrowserNavigatorImpl
import com.example.newsapp.domain.share.ShareManager
import com.example.newsapp.data.share.ShareManagerImpl
import com.example.newsapp.domain.usecase.OpenUrlUseCase
import com.example.newsapp.domain.usecase.ShareArticleUseCase
import kotlin.getValue

class App : Application() {

    private val newsApi: NewsApi by lazy { RetrofitClient.newsApiService }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImpl(newsApi = newsApi)
    }
    private val articleDao: ArticleDao by lazy { ArticleDao }
    private val localDataSource: LocalDataSource by lazy {
        LocalDataSourceImpl(articleDao = articleDao)
    }
    private val dataModelsMapper by lazy { DataModelsMapper }
    private val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = dataModelsMapper
        )
    }
    private val browserNavigator: BrowserNavigator by lazy {
        BrowserNavigatorImpl(context = this)
    }
    private val shareManager: ShareManager by lazy {
        ShareManagerImpl(context = this)
    }

    val uiModelsMapper by lazy { UiModelsMapper }

    val openUrlUseCase: OpenUrlUseCase by lazy { OpenUrlUseCase(browserNavigator) }
    val shareArticleUseCase: ShareArticleUseCase by lazy { ShareArticleUseCase(shareManager) }

    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase by lazy {
        GetTopHeadlinesUseCase(
            articleRepository = articleRepository,
        )
    }

    val getDetailArticleUseCase: GetDetailArticleUseCase by lazy {
        GetDetailArticleUseCase(
            articleRepository = articleRepository,
        )
    }

    val saveDetailArticleUseCase: SaveDetailArticleUseCase by lazy {
        SaveDetailArticleUseCase(
            articleRepository = articleRepository,
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