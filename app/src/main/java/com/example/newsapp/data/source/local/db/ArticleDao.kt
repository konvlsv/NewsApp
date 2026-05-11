package com.example.newsapp.data.source.local.db

import com.example.newsapp.data.source.local.models.ArticleLocalModel

object ArticleDao {
    private var article: ArticleLocalModel? = null

    fun saveDetailArticle(article: ArticleLocalModel) {
        this.article = article
    }

    fun getDetailArticle(): ArticleLocalModel {
        return article ?: throw NullPointerException("Article is null")
    }
}