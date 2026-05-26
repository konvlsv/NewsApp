package com.example.newsapp.data.source.local.db

import com.example.newsapp.data.source.local.models.ArticleEntity

object ArticleDao {
    private var article: ArticleEntity? = null

    fun saveDetailsArticle(article: ArticleEntity) {
        this.article = article
    }

    fun getDetailsArticle(): ArticleEntity {
        return article ?: throw NullPointerException("Article is null")
    }
}