package com.example.newsapp.ui.navigation

import android.content.Context

interface BrowserNavigator {
    fun openUrl(context: Context,url: String)
}