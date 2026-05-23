package com.example.newsapp.domain.share

interface ShareManager {
    fun shareArticle(title: String, description: String, url: String)
}