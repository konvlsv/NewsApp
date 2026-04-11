package com.example.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.models.ArticleUi
import com.example.newsapp.ui.models.getMockArticleUiList
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ArticleCardCollapsed(
    articleUi: ArticleUi,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onCardClick,
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            // ПозволяетRow измерить высоту внутреннего контента (Column)
            // и передать это значение для fillMaxHeight у Image
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Article picture",
                modifier = Modifier
                    .weight(1f) // Занимает 1 часть из 5 (20% ширины)
                    .fillMaxHeight(), // Растягивается на всю высоту колонки рядом
                contentScale = ContentScale.Crop // Чтобы картинка не искажалась
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(4f) // Занимает 4 части из 5 (80% ширины)
                    .padding(end = 8.dp, top = 8.dp, bottom = 8.dp) // Небольшие отступы
            ) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(
                        text = articleUi.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
                Text(
                    text = articleUi.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row {
                    Text(
                        text = articleUi.author,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = articleUi.publishedAt,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ArticleCardCollapsedPreview(){
    NewsAppTheme(){
        ArticleCardCollapsed(
            articleUi = getMockArticleUiList().first(),
            onCardClick = {},
        )
    }
}