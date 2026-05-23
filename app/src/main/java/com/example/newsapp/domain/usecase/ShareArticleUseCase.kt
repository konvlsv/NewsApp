package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.share.ShareManager

class ShareArticleUseCase(
    private val shareManager: ShareManager
) {
    operator fun invoke(title: String, description: String, url: String) {
        shareManager.shareArticle(title, description, url)
    }
}