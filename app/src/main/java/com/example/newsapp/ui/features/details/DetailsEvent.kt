package com.example.newsapp.ui.features.details

import com.example.newsapp.ui.common.models.ArticleUi

sealed interface DetailsEvent {
    data object OnRefresh: DetailsEvent
    data class OnOpenInBrowser(val article: ArticleUi): DetailsEvent
    data class OnShare(val article: ArticleUi): DetailsEvent
}
