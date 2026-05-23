package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.repository.ArticleRepository

class SaveDetailArticleUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(article: Article) {
        articleRepository.saveDetailArticle(article)
    }
}