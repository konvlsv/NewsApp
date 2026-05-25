package com.example.newsapp.ui.common.preview

import com.example.newsapp.ui.common.models.ArticleUi
import com.example.newsapp.ui.features.articles.ArticlesState
import kotlin.random.Random

fun getMockArticlesState() = ArticlesState(articles = getMockArticleUiList())

fun getMockArticleUiList(): List<ArticleUi> {
    val articles = mutableListOf<ArticleUi>()
    repeat(100) { index ->
        articles.add(
            ArticleUi(
                id = "id_$index",
                sourceName = "Chanel ${generateRandomText(1).removeSuffix(".")}",
                author = "Author №${index + 1}",
                content = generateRandomText(Random.nextInt(35, 55)),
                description = generateRandomText(Random.nextInt(15, 25)),
                publishedAt = "12.04.2024",
                title = generateRandomText(Random.nextInt(3, 7)),
                articleUrl = "https://example.com/article/$index",
                imageUrl = "https://picsum.photos/seed/${index}/400/300"
            )
        )
    }
    return articles
}

private fun generateRandomText(wordCount: Int): String {
    val sourceWords = listOf(
        "news", "development", "technology", "android", "application", "design",
        "architecture", "programming", "code", "interface", "user",
        "system", "update", "function", "smartphone", "world", "event", "market"
    )

    return (1..wordCount)
        .map { sourceWords.random() }
        .joinToString(" ")
        .replaceFirstChar { it.uppercase() } + "."
}