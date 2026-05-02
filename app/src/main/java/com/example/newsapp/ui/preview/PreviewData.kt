package com.example.newsapp.ui.preview

import com.example.newsapp.ui.models.ArticleCategory
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.state.NewsUiState
import kotlin.random.Random

fun getMockSuccessNewsUiState(): NewsUiState.Success {
    return NewsUiState.Success(
        articles = getMockArticleUiList(),
        searchQuery = "",
        selectedCategory = ArticleCategory.GENERAL,
        expandedCards = emptySet(),
        detailsArticle = null
    )
}

fun getMockArticleUiList(): List<ArticleDisplayModel> {
    val articles = mutableListOf<ArticleDisplayModel>()
    repeat(100) { index ->
        articles.add(
            ArticleDisplayModel(
                id = index.toString(),
                name = "Chanel ${generateRandomText(1).removeSuffix(".")}",
                author = "Author №${index + 1}",
                content = generateRandomText(Random.nextInt(35, 55)),
                description = generateRandomText(Random.nextInt(15, 25)),
                publishedAt = "12.04.2024",
                title = generateRandomText(Random.nextInt(3, 7)),
                url = "https://example.com",
                urlToImage = "https://picsum.photos/seed/${index}/400/300"
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