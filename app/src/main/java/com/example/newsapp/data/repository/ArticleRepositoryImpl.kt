package com.example.newsapp.data.repository

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.mapper.DataModelsMapper
import com.example.newsapp.data.source.remote.RemoteDataSource
import com.example.newsapp.domain.exception.DomainException
import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel
import com.example.newsapp.domain.repository.ArticleRepository
import kotlin.coroutines.cancellation.CancellationException

class ArticleRepositoryImpl(
    val remoteDataSource: RemoteDataSource,
    val mapper: DataModelsMapper
) : ArticleRepository {
    override suspend fun getTopHeadlines(query: ArticleQueryDomainModel): List<ArticleDomainModel> {
        return try {
            val remoteQuery = mapper.matToArticleQueryRemoteModel(query)
            val response = remoteDataSource.getTopHeadlines(remoteQuery)
            mapper.mapToArticleDomainModel(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: RemoteException) {
            throw when (e) {
                is RemoteException.NoInternetException -> DomainException.NetworkException()
                is RemoteException.TimeoutException -> DomainException.TimeoutException()
                is RemoteException.ServerException -> DomainException.ServerException()
                is RemoteException.ParseException -> DomainException.ParseException()
                is RemoteException.UnknownException -> DomainException.UnknownException(e.message ?: "")
            }
        } catch (e: Exception) {
            throw DomainException.UnknownException(e.message ?: "")
        }
    }
}