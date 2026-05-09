package com.example.newsapp.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.R
import com.example.newsapp.ui.components.NewsTopAppBar
import com.example.newsapp.ui.models.ArticleDisplayModel
import com.example.newsapp.ui.navigation.AppScreens
import com.example.newsapp.ui.navigation.titleRes
import com.example.newsapp.ui.screens.ArticleDetailsScreen
import com.example.newsapp.ui.screens.NewsListScreen
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack =
        backStackEntry?.destination?.hasRoute<AppScreens.NewsListScreen>() == false
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            NewsTopAppBar(
                title = stringResource(backStackEntry?.destination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.NewsListScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<AppScreens.NewsListScreen> {
                NewsListScreen(
                    onNavigateToArticleDetails = { article ->
                        navController.navigate(AppScreens.ArticleDetailsScreen(article))
                    },
                    onShareClick = { article ->
                        onShareArticle(article, context)
                    }
                )
            }
            composable<AppScreens.ArticleDetailsScreen>(
                typeMap = mapOf(typeOf<ArticleDisplayModel>() to ArticleDisplayModel.NavigationType)
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<AppScreens.ArticleDetailsScreen>()
                ArticleDetailsScreen(
                    article = route.article,
                    onShareClick = { article ->
                        onShareArticle(article, context)
                    }
                )
            }
        }
    }
}

private fun onShareArticle(article: ArticleDisplayModel, context: Context){
    val shareTitle = context.getString(R.string.share)
    val shareText = "${article.title}\n\n${article.url}"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    context.startActivity(Intent.createChooser(intent, shareTitle))
}