package com.example.newsapp.domain.repository

import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel

interface ArticleRepository {

    suspend fun getTopHeadlines(query: ArticleQueryDomainModel): List<ArticleDomainModel>
    suspend fun saveDetailArticle(article: ArticleDomainModel)
    suspend fun getDetailArticle(): ArticleDomainModel
}