package com.example.newsapp.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.newsapp.R
import com.example.newsapp.ui.models.ArticleDisplayModel
import kotlinx.serialization.Serializable

sealed interface AppScreens {
    @Serializable
    data object NewsListScreen : AppScreens

    @Serializable
    data class ArticleDetailsScreen(val article: ArticleDisplayModel) : AppScreens
}

val NavDestination?.titleRes: Int
    get() = when {
        this?.hasRoute<AppScreens.ArticleDetailsScreen>() == true -> R.string.article_details_screen
        else -> R.string.news_list_screen
    }