package com.example.newsapp.ui.models

import kotlin.random.Random

data class ArticleUi(
    val id: String,
    val name: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)

fun getMockArticleUiList(): List<ArticleUi> {
    val articles = mutableListOf<ArticleUi>()
    repeat(100) { index ->
        articles.add(
            ArticleUi(
                id = index.toString(),
                name = "Канал ${generateRandomText(1).removeSuffix(".")}",
                author = "Автор №${index + 1}",
                content = (1..5).joinToString("\n\n") { generateRandomText(Random.nextInt(10, 20)) },
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
        "новости", "разработка", "технологии", "андроид", "приложение", "дизайн",
        "архитектура", "программирование", "код", "интерфейс", "пользователь",
        "система", "обновление", "функция", "смартфон", "мир", "событие", "рынок"
    )

    return (1..wordCount)
        .map { sourceWords.random() }
        .joinToString(" ")
        .replaceFirstChar { it.uppercase() } + "."
}
