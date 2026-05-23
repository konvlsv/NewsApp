package com.example.newsapp.ui.features.articles

sealed interface ArticlesNavigationTarget {
    object TargetToDetails : ArticlesNavigationTarget
}