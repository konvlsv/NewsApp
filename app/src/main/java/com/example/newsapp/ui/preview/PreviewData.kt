package com.example.newsapp.ui.preview

import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.models.ArticleQueryDisplayModel
import com.example.newsapp.ui.state.NewsState
import com.example.newsapp.ui.state.UiState
import kotlin.random.Random

fun getMockSuccessNewsUiState(): UiState.Success<NewsState> {
    return UiState.Success(
        data = NewsState(
            articles = getMockArticleUiList(),
            articleQuery = ArticleQueryDisplayModel(),
            expandedCards = emptySet(),
        )
    )
}

fun getMockArticleUiList(): List<ArticleDisplayModel> {
    val articles = mutableListOf<ArticleDisplayModel>()
    repeat(100) { index ->
        articles.add(
            ArticleDisplayModel(
                id = "id_$index",
                name = "Chanel ${generateRandomText(1).removeSuffix(".")}",
                author = "Author №${index + 1}",
                content = generateRandomText(Random.nextInt(35, 55)),
                description = generateRandomText(Random.nextInt(15, 25)),
                publishedAt = "12.04.2024",
                title = generateRandomText(Random.nextInt(3, 7)),
                url = "https://example.com/article/$index",
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