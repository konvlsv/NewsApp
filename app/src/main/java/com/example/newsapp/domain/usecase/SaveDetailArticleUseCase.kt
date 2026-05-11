package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class SaveDetailArticleUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(article: ArticleDomainModel) {
        articleRepository.saveDetailArticle(article)
    }
}