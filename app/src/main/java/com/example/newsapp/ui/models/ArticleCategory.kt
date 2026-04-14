package com.example.newsapp.ui.models

import androidx.annotation.StringRes
import com.example.newsapp.R

enum class ArticleCategory(
    val id: String,          // То, что отправляем в API
    @param:StringRes val label: Int // Ссылка на strings.xml
) {
    BUSINESS("business", R.string.business),
    ENTERTAINMENT("entertainment", R.string.entertainment),
    GENERAL("general", R.string.general),
    HEALTH("health", R.string.health),
    SCIENCE("science", R.string.science),
    SPORTS("sports", R.string.sports),
    TECHNOLOGY("technology", R.string.technology)
}