package com.example.newsapp.domain.repository

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery

interface ArticleRepository {

    suspend fun getTopHeadlines(query: ArticleQuery): List<Article>
    suspend fun saveDetailArticle(article: Article)
    suspend fun getDetailArticle(): Article
}