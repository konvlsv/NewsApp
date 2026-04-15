package com.example.newsapp.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleSearchBar(
    articleSearchBarSearchQuery: String,
    onArticleSearchBarValueChange: (String) -> Unit,
    onArticleSearchBarDeleteClick: () -> Unit,
    onArticleSearchBarSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = articleSearchBarSearchQuery,
        onValueChange = { onArticleSearchBarValueChange(it) },
        placeholder = { Text(stringResource(R.string.search)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (articleSearchBarSearchQuery.isNotEmpty()) {
                IconButton(onClick = { onArticleSearchBarDeleteClick() }) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.clear))
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onArticleSearchBarSearchClick() }),
        shape = MaterialTheme.shapes.small,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
)
@Composable
fun ArticleSearchBarPreview() {
    NewsAppTheme() {
        ArticleSearchBar(
            articleSearchBarSearchQuery = "asdasd",
            onArticleSearchBarValueChange = {},
            onArticleSearchBarDeleteClick = {},
            onArticleSearchBarSearchClick = {}
        )
    }
}