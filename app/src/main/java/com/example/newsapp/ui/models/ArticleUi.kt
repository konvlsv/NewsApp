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

fun getMockArticleUiList(): List<Unit> {
    val faker = faker { }
    return listOf(
        repeat(10){
            ArticleUi(
                id = Random.nextInt().toString(),
                name = faker.app.name(),
                author = faker.name.name(),
                content = faker.elderScrolls.region(),
                description = faker.warhammerFantasy.heroes(),
                publishedAt = "2022-01-01T00:00:00Z",
                title = faker.app.name(),
                url = faker.dog.name(),
                urlToImage = faker.cat.name(),
            )
        }
    )
}
