package com.example.newsapp.ui.navigation

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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.common.components.NewsTopAppBar
import com.example.newsapp.ui.features.details.DetailsScreen
import com.example.newsapp.ui.features.articles.ArticlesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack =
        backStackEntry?.destination?.hasRoute<Screens.Articles>() == false
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
            startDestination = Screens.Articles,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Screens.Articles> {
                ArticlesScreen(
                    onNavigateToArticleDetails = {
                        navController.navigate(Screens.Details)
                    },
                )
            }
            composable<Screens.Details> {
                DetailsScreen()
            }
        }
    }
}