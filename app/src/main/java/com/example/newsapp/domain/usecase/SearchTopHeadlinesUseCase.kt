package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.mapper.DomainModelsMapper
import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.ui.models.ArticleDisplayModel

class SearchTopHeadlinesUseCase(
    private val articleRepository: ArticleRepository,
    private val mapper: DomainModelsMapper
) {
    suspend operator fun invoke(query: String, category: ArticleCategoryDomainModel): List<ArticleDisplayModel> {
        return mapper.toDisplayModels(articleRepository.searchTopHeadlines(query, category))
    }
}