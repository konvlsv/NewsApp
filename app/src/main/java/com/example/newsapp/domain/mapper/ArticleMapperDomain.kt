package com.example.newsapp.domain.mapper

import com.example.newsapp.domain.models.ArticleDomainModel
import com.example.newsapp.ui.models.ArticleDisplayModel

object ArticleMapperDomain {
    fun toDisplayModels(domainModels: List<ArticleDomainModel>): List<ArticleDisplayModel> {
        return domainModels.map { domainModel ->
            ArticleDisplayModel(
                id = domainModel.id,
                name = domainModel.name,
                author = domainModel.author,
                content = domainModel.content,
                description = domainModel.description,
                publishedAt = domainModel.publishedAt,
                title = domainModel.title,
                url = domainModel.url,
                urlToImage = domainModel.urlToImage,
            )
        }
    }
}