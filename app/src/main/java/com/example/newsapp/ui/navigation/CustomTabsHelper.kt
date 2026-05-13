package com.example.newsapp.ui.navigation

import android.content.Context
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

object CustomTabsHelper: BrowserNavigator {

    override fun openUrl(context: Context,url: String) {
        if (url.isEmpty()) {
            return
        }

        try {
            val customTabsIntent = CustomTabsIntent.Builder().apply {
                setShowTitle(true)
                setUrlBarHidingEnabled(true)
                setShareState(CustomTabsIntent.SHARE_STATE_ON)
            }.build()
            customTabsIntent.intent.setPackage("com.android.chrome")
            customTabsIntent.launchUrl(context, url.toUri())

        } catch (e: Exception) {
            try {
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    url.toUri()
                )
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Error opening url", Toast.LENGTH_SHORT).show()
            }
        }
    }
}