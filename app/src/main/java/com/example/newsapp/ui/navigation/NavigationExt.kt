package com.example.newsapp.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.newsapp.R

val NavDestination?.titleRes: Int
    get() = when {
        this?.hasRoute<AppScreens.ArticleDetailsScreen>() == true -> R.string.article_details_screen
        else -> R.string.news_list_screen
    }