package com.example.eduhub.ui.modules.detail

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.eduhub.R
import com.example.eduhub.ui.theme.EduHubTheme

@Composable
fun CreatorInfo(
    name: String,
    image: String?
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Author",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (image.isNullOrEmpty()) {
                Icon(
                    painterResource(R.drawable.account_circle_fill),
                    contentDescription = "Author image",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            } else {
                AsyncImage(
                    model = image,
                    contentDescription = "Author image",
                    modifier = Modifier
                        .padding(8.dp)
                        .width(48.dp)
                        .height(48.dp)
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun SummaryModule(
    summary: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "About this Module",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = summary,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ContentModule(
    title: String,
    content: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ModuleDetailScreen(
    scrollBehavior: TopAppBarScrollBehavior?,
    moduleId: String,
    viewModel: ModuleDetailViewModel
) {
    val state = viewModel.state
    LaunchedEffect(moduleId) {
        viewModel.loadDetailModule(moduleId)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text("Loading..")
        }
    } else if (state.error != null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text("Error: ${state.error}")
        }
    } else  {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .then(
                        if (scrollBehavior != null) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                        else Modifier
                    ),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    CreatorInfo(
                        name = state.module.createdBy.name,
                        image = state.module.createdBy.image
                    )
                }
                item {
                    SummaryModule(
                        summary = state.module.summary
                    )
                }
                item {
                    ContentModule(
                        title = state.module.title,
                        content = state.module.content
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ModuleDetailScreenPreview() {
    EduHubTheme {
        ModuleDetailScreen(
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            moduleId = TODO(),
            viewModel = TODO()
        )
    }
}