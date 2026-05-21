package com.example.newsapp.data.source.remote.api

import com.example.newsapp.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue
import kotlin.jvm.java

object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/"
    private const val API_KEY = BuildConfig.NEWS_API_KEY

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val urlWithKey = originalUrl.newBuilder()
                .addQueryParameter("apiKey", API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(urlWithKey)
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    val newsApiService: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NewsApi::class.java)
    }
}