package com.example.newsapp.data.repository

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.source.local.LocalDataSource
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery
import com.example.newsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val mapper: DataModelsMapper
) : ArticleRepository {
    override suspend fun getTopHeadlines(query: ArticleQuery): List<Article> {
        return try {
            val remoteQuery = mapper.toArticleQueryRequest(query)
            val response = remoteDataSource.getTopHeadlines(remoteQuery)
            mapper.toArticle(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: RemoteException) {
            throw when (e) {
                is RemoteException.NoInternetException -> DomainException.NetworkException()
                is RemoteException.TimeoutException -> DomainException.TimeoutException()
                is RemoteException.ServerException -> DomainException.ServerException()
                is RemoteException.ParseException -> DomainException.ParseException()
                is RemoteException.UnknownException -> DomainException.UnknownException(
                    e.message ?: ""
                )
            }
        } catch (e: Exception) {
            throw DomainException.UnknownException(e.message ?: "")
        }
    }

    override suspend fun saveDetailsArticle(article: Article) {
        localDataSource.saveDetailsArticle(mapper.toArticleEntity(article))
    }

    override fun getDetailsArticle(): Flow<Article> {
        return localDataSource.getDetailsArticle().map { mapper.toArticle(it) }
    }
}