package com.example.newsapp.data

import com.example.newsapp.data.source.network.NetworkRepository
import com.example.newsapp.data.source.network.models.NewsApiResponse
import com.example.newsapp.ui.models.ArticleDisplayModel

class DataRepositoryImpl(
    val networkRepository: NetworkRepository
) : DataRepository {

    override suspend fun getArticles(
        query: String,
        category: String
    ): List<ArticleDisplayModel> {
        val response = networkRepository.getArticles(query, category)
        return response.toArticleDisplayModel()
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