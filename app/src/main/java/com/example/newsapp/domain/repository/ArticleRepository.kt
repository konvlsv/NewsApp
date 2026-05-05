package com.example.newsapp.domain.repository

import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.models.ArticleDomainModel

interface ArticleRepository {

    suspend fun getCategoryTopHeadlines(category: ArticleCategoryDomainModel): List<ArticleDomainModel>
    suspend fun searchTopHeadlines(query: String, category: ArticleCategoryDomainModel): List<ArticleDomainModel>
}