package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class GetDetailArticleUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(): ArticleDomainModel {
        return articleRepository.getDetailArticle()
    }
}