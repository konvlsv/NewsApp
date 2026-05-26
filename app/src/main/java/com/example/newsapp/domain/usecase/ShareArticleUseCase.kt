package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.share.ShareManager

class ShareArticleUseCase(
    private val shareManager: ShareManager
) {
    fun share(title: String, description: String, url: String) {
        shareManager.shareArticle(title, description, url)
    }
}