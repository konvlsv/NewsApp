package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.db.ArticleDao
import com.example.newsapp.data.source.local.models.ArticleEntity

class LocalDataSourceImpl(
    private val articleDao: ArticleDao
): LocalDataSource {
    override suspend fun saveDetailArticle(article: ArticleEntity) {
        articleDao.saveDetailArticle(article)
    }

    override suspend fun getDetailArticle(): ArticleEntity {
        return articleDao.getDetailArticle()
    }
}