package com.example.eduhub.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eduhub.ui.modules.list.ModuleItem
import com.example.eduhub.ui.modules.list.ModuleItemState
import com.example.eduhub.ui.modules.list.ModuleListViewModel
import com.example.eduhub.ui.theme.EduHubTheme
import com.example.eduhub.ui.topics.TopicItem
import com.example.eduhub.ui.topics.TopicItemState
import com.example.eduhub.ui.topics.TopicViewModel

@Composable
fun TopicList(
    viewModel: TopicViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Topics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                state.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(24.dp)
                                .height(24.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.error != null -> {
                    item {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
                state.filteredTopics.isEmpty() -> {
                    item {
                        Text(
                            text = "No topics found",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
                else -> {
                    items(state.filteredTopics) {
                        TopicItem(
                            topic = TopicItemState(
                                id = it.id,
                                name = it.name,
                                icon = it.icon
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModuleList(
    isLoading: Boolean = false,
    error: String? = null,
    modules: List<ModuleItemState>,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "All modules",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            when {
                isLoading -> {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .width(24.dp)
                        .height(24.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }

                modules.isEmpty() -> {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .width(24.dp)
                        .height(24.dp)
                    ) {
                        Text(
                            text = "No modules found",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }

                else -> {
                    modules.forEach { module ->
                        ModuleItem(
                            onNavigateToDetail = { onNavigateToDetail(module.id) },
                            module = ModuleItemState(
                                id = module.id,
                                title = module.title,
                                summary = module.summary,
                                createdBy = module.createdBy
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: ModuleListViewModel = hiltViewModel(),
    onNavigateToDetail: () -> Unit
) {
    val state = viewModel.state

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = "Explore",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
                    )
                }
            }

            item {
                TopicList()
            }

            item {
                ModuleList(
                    isLoading = state.isLoading,
                    error = state.error,
                    modules = state.filteredModules,
                    onNavigateToDetail = {onNavigateToDetail()}
                )
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    EduHubTheme {
        HomeScreen(
            onNavigateToDetail = {}
        )
    }
}