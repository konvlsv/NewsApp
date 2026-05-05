package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.mapper.ArticleMapperDomain
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.ui.models.ArticleDisplayModel

class GetCategoryTopHeadlinesUseCase(
    private val articleRepository: ArticleRepository,
    private val mapper: ArticleMapperDomain
) {
    suspend operator fun invoke(category: String): List<ArticleDisplayModel> {
        return mapper.toDisplayModels(articleRepository.getCategoryTopHeadlines(category))
    }
}