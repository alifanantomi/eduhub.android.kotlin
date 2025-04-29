package com.example.eduhub.ui.modules.list

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.eduhub.data.api.model.response.ModuleItem
import com.example.eduhub.ui.theme.EduHubTheme

@Composable
fun ModuleItem(
    module: ModuleItemState,
    viewModel: ModuleItemViewModel = viewModel(),
    onNavigateToDetail: (String) -> Unit,
) {
    if (viewModel.navigateToDetail) {
        viewModel.onNavigatedToDetail()

        onNavigateToDetail(module.id)
    }

    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)
            ) {
                if (!module.createdBy.image.isNullOrEmpty()) {
                    AsyncImage(
                        model = module.createdBy.image,
                        contentDescription = "Author image",
                        modifier = Modifier
                            .padding(8.dp)
                            .height(24.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Author image",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
                Text(
                    text = module.createdBy.name,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = module.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = module.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append("1 min")
                            }
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    letterSpacing = 6.sp
                                )
                            ) {
                                append(" • ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                )
                            ) {
                                append("0% ")
                            }
                            append("Completed")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    progress = { (2.toFloat() / 100) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                )

                Button(
                    onClick = viewModel::onDetailClick,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Read module",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ModuleItemPreview() {
    EduHubTheme {
        ModuleItem(
            module = ModuleItemState(
                title = "Module Title",
                summary = "Module summary for data for text example, this is one of the example that has two lines of paragraph maybe it works and looks better with long text but truncate on two lines",
                image = "",
                createdBy = CreatorState(
                    name = "Author Name",
                    image = ""
                )
            ),
            onNavigateToDetail = {}
        )
    }
}
