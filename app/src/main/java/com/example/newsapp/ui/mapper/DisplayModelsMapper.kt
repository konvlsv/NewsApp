package com.example.newsapp.ui.mapper

import com.example.newsapp.domain.models.ArticleCategoryDomainModel
import com.example.newsapp.ui.models.ArticleCategoryDisplayModel

object DisplayModelsMapper {
    fun toDomainModel(articleCategoryDisplayModel: ArticleCategoryDisplayModel): ArticleCategoryDomainModel =
        when (articleCategoryDisplayModel) {
            ArticleCategoryDisplayModel.GENERAL -> ArticleCategoryDomainModel.GENERAL
            ArticleCategoryDisplayModel.BUSINESS -> ArticleCategoryDomainModel.BUSINESS
            ArticleCategoryDisplayModel.ENTERTAINMENT -> ArticleCategoryDomainModel.ENTERTAINMENT
            ArticleCategoryDisplayModel.HEALTH -> ArticleCategoryDomainModel.HEALTH
            ArticleCategoryDisplayModel.SCIENCE -> ArticleCategoryDomainModel.SCIENCE
            ArticleCategoryDisplayModel.SPORTS -> ArticleCategoryDomainModel.SPORTS
            ArticleCategoryDisplayModel.TECHNOLOGY -> ArticleCategoryDomainModel.TECHNOLOGY
        }
}