package com.example.newsapp.ui.navigation

import android.content.Context
import android.content.Intent
import com.example.newsapp.R

class ShareManagerImpl(
    private val context: Context
) : ShareManager {
    override fun shareArticle(
        title: String,
        description: String,
        url: String
    ) {
        val shareText = buildString {
            append(title)
            if (description.isNotBlank()) {
                append("\n\n")
                append(description)
            }
            append("\n\n")
            append(url)
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, shareText)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        val chooserTitle = context.getString(R.string.share_title)
        val chooser = Intent.createChooser(intent, chooserTitle).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooser)
        } else {
            context.startActivity(chooser)
        }
    }
}