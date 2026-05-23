package com.example.newsapp.data.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.example.newsapp.domain.navigation.BrowserNavigator

class BrowserNavigatorImpl(
    private val context: Context
): BrowserNavigator {
    override fun openUrl(url: String) {
        if (url.isBlank()) return
        try {
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            val uri = formattedUrl.toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("BrowserNavigator", "Failed to open URL: $url", e)
        }
    }
}