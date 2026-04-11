package com.example.newsapp.ui.models

import io.github.serpro69.kfaker.faker
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
    val faker = faker { }
    val articles = mutableListOf<ArticleUi>()
    repeat(100) {
        articles.add(
            ArticleUi(
                id = Random.nextInt().toString(),
                name = faker.app.name(),
                author = faker.name.name(),
                content = faker.elderScrolls.region(),
                description = faker.warhammerFantasy.heroes(),
                publishedAt = "2022-01-01",
                title = faker.app.name(),
                url = "https://www.google.com",
                urlToImage = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Android_Studio_Logo_%282023%29.svg/1920px-Android_Studio_Logo_%282023%29.svg.png",
            )
        )
    }
    return articles
}
