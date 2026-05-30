package com.example.newsapp.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.source.local.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailArticle(article: ArticleEntity): Long

    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleById(id: Int): Flow<ArticleEntity>
}