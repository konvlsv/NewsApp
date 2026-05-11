package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.db.ArticleDao
import com.example.newsapp.data.source.local.models.ArticleLocalModel

class LocalDataSourceImpl(
    private val articleDao: ArticleDao
): LocalDataSource {
    override suspend fun saveDetailArticle(article: ArticleLocalModel) {
        articleDao.saveDetailArticle(article)
    }

    override suspend fun getDetailArticle(): ArticleLocalModel {
        return articleDao.getDetailArticle()
    }
}