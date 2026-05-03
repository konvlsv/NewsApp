package com.example.newsapp.data

import com.example.newsapp.data.exception.RemoteException
import com.example.newsapp.data.source.remote.RemoteRepository
import com.example.newsapp.data.source.remote.models.NewsApiResponse
import com.example.newsapp.ui.models.ArticleDisplayModel

class DataRepositoryImpl(
    val remoteRepository: RemoteRepository
) : DataRepository {

    override suspend fun getArticles(
        query: String,
        category: String,
        country: String
    ): List<ArticleDisplayModel> {
        return try {
            val response = remoteRepository.getArticles(query, category, country)
            response.toArticleDisplayModel()
        } catch (e: Exception) {
            throw RemoteException.ParseException()
        }
    }
}

fun NewsApiResponse.toArticleDisplayModel(): List<ArticleDisplayModel> {
    val displayModels = mutableListOf<ArticleDisplayModel>()
    this.articles?.forEach { apiArticle ->
        displayModels.add(
            ArticleDisplayModel(
                id = apiArticle.source?.id ?: "",
                name = apiArticle.source?.name ?: "",
                author = apiArticle.author ?: "",
                content = apiArticle.content ?: "",
                description = apiArticle.description ?: "",
                publishedAt = apiArticle.publishedAt ?: "",
                title = apiArticle.title ?: "",
                url = apiArticle.url ?: "",
                urlToImage = apiArticle.urlToImage ?: "",
            )
        )
    }
    return displayModels
}