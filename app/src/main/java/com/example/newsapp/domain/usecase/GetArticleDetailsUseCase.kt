package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleDetailsUseCase(
    private val articleRepository: ArticleRepository
) {
    fun getArticleDetails(): Flow<Article> {
        return articleRepository.getDetailsArticle()
    }
}