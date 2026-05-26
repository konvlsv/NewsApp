package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.repository.ArticleRepository

class GetArticleDetailsUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(): Article {
        return articleRepository.getDetailsArticle()
    }
}