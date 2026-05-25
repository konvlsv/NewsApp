package com.example.newsapp.ui.features.details

import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.state.ErrorState

data class DetailsState(
    val article: ArticleUi? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorState: ErrorState? = null,
) {
    val isError: Boolean get() = errorState != null
}