package com.example.newsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppScreens {
    @Serializable
    data object NewsListScreen : AppScreens

    @Serializable
    data object ArticleDetailsScreen : AppScreens
}