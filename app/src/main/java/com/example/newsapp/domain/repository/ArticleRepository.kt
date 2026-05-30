package com.example.newsapp.domain.repository

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    suspend fun getTopHeadlines(query: ArticleQuery): List<Article>
    suspend fun saveDetailsArticle(article: Article)
    fun getDetailsArticle(): Flow<Article>
}