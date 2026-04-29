package com.example.newsapp.ui

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.ui.components.NewsTopAppBar
import com.example.newsapp.ui.navigation.AppScreens
import com.example.newsapp.ui.screens.ArticleDetailsScreen
import com.example.newsapp.ui.screens.NewsListScreen
import com.example.newsapp.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController(),
) {
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
                title = stringResource(R.string.app_name),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        val viewModel: NewsViewModel = viewModel<NewsViewModel>()
        NavHost(
            navController = navController,
            startDestination = AppScreens.NewsListScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<AppScreens.NewsListScreen> {
                NewsListScreen(
                    onNavigateToArticleDetails = {
                        navController.navigate(AppScreens.ArticleDetailsScreen)
                    },
                    viewModel = viewModel
                )
            }
            composable<AppScreens.ArticleDetailsScreen> {
                ArticleDetailsScreen(viewModel = viewModel)
            }
        }
    }
}