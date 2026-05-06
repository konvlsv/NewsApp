package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.domain.models.ArticleQueryDomainModel
import com.example.newsapp.domain.repository.ArticleRepository

class GetTopHeadlinesUseCase(
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke(query: ArticleQueryDomainModel): List<ArticleDomainModel> {
        return articleRepository.getTopHeadlines(query)
    }
}