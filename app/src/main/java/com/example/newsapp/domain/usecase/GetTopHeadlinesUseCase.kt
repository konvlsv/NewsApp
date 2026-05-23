package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.ArticleQuery
import com.example.newsapp.domain.repository.ArticleRepository

class GetTopHeadlinesUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke(query: ArticleQuery): List<Article> {
        return articleRepository.getTopHeadlines(query)
    }
}