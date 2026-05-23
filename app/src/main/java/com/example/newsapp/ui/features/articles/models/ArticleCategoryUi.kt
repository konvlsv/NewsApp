package com.example.newsapp.ui.features.articles.models

import androidx.annotation.StringRes
import com.example.newsapp.R

enum class ArticleCategoryUi(
    @param:StringRes val label: Int
) {
    GENERAL(R.string.general),
    BUSINESS(R.string.business),
    ENTERTAINMENT(R.string.entertainment),
    HEALTH(R.string.health),
    SCIENCE(R.string.science),
    SPORTS(R.string.sports),
    TECHNOLOGY(R.string.technology)
}