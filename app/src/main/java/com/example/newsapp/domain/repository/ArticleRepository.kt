package com.example.newsapp.domain.repository

import com.example.newsapp.domain.models.ArticleDomainModel

interface ArticleRepository {

    suspend fun getCategoryTopHeadlines(category: String): List<ArticleDomainModel>
    suspend fun searchTopHeadlines(query: String, category: String): List<ArticleDomainModel>
}