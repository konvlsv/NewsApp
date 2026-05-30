package com.example.newsapp.data.source.local

import com.example.newsapp.data.source.local.db.ArticleDao
import com.example.newsapp.data.source.local.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val articleDao: ArticleDao
) : LocalDataSource {
    override suspend fun saveDetailsArticle(article: ArticleEntity) {
        articleDao.insertDetailArticle(article.copy(id = 1))
    }

    override fun getDetailsArticle(): Flow<ArticleEntity> {
        return articleDao.getArticleById(1)
    }
}