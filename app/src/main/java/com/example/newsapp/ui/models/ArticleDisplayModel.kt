package com.example.newsapp.ui.models

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ArticleDisplayModel(
    val id: String,
    val name: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
) {
    companion object {
        val NavigationType = object : NavType<ArticleDisplayModel>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): ArticleDisplayModel? {
                return bundle.getString(key)?.let { Json.decodeFromString(it) }
            }

            override fun parseValue(value: String): ArticleDisplayModel {
                return Json.decodeFromString(Uri.decode(value))
            }

            override fun serializeAsValue(value: ArticleDisplayModel): String {
                return Uri.encode(Json.encodeToString(value))
            }

            override fun put(bundle: Bundle, key: String, value: ArticleDisplayModel) {
                bundle.putString(key, Json.encodeToString(value))
            }
        }
    }
}
