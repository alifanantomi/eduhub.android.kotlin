package com.example.eduhub.ui.modules.list

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ModuleListScreen(
    viewModel: ModuleListViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val state = viewModel.state

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            stickyHeader {
                ModuleListHeader()
            }

            when {
                state.isLoading -> {
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .width(24.dp)
                            .height(24.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                state.error != null ->  {
                    item {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
                state.filteredModules.isEmpty() -> {
                    item {
                        Text(
                            text = "No modules found",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
                else -> {
                    items(state.filteredModules) { module ->
                        ModuleItem(
                            module = ModuleItemState(
                                id = module.id,
                                title = module.title,
                                summary = module.summary,
                                createdBy = module.createdBy
                            ),
                            onNavigateToDetail = {
                                onNavigateToDetail(module.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ModuleListHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "All modules",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ModuleListScreenPreview() {
    ModuleListScreen(
        onNavigateToDetail = {}
    )
}

