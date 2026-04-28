package com.example.newsapp.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.ui.screens.NewsListScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.viewmodels.NewsViewModel
import kotlinx.serialization.Serializable

sealed interface AppScreens {
    @Serializable
    data object NewsListScreen : AppScreens

    @Serializable
    data object ArticleDetailsScreen : AppScreens
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(
    navController: NavHostController = rememberNavController(),
    viewModel: NewsViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        NewsListScreen(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@ExperimentalMaterial3Api
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    backgroundColor = 0xFFFFF9EE
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    backgroundColor = 0xFF15130B
)
@Composable
fun NewsAppPreview() {
    NewsAppTheme() {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(R.string.app_name))
                    },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
            NewsListScreen(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}