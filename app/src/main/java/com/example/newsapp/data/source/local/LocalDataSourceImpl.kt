package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.db.ArticleDao
import com.example.newsapp.data.source.local.models.ArticleEntity

class LocalDataSourceImpl(
    private val articleDao: ArticleDao
): LocalDataSource {
    override suspend fun saveDetailsArticle(article: ArticleEntity) {
        articleDao.saveDetailsArticle(article)
    }

    override suspend fun getDetailsArticle(): ArticleEntity {
        return articleDao.getDetailsArticle()
    }
}