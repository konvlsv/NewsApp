package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.navigation.BrowserNavigator

class OpenUrlUseCase(
    private val browserNavigator: BrowserNavigator
) {
    operator fun invoke(url: String){
        browserNavigator.openUrl(url)
    }
}