package com.example.eduhub.ui.profile

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.eduhub.R
import com.example.eduhub.ui.modules.list.ModuleItem
import com.example.eduhub.ui.theme.EduHubTheme

@Composable
fun AuthorInfo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Princeton_seal.svg/1024px-Princeton_seal.svg.png",
                contentDescription = "Author image",
                modifier = Modifier
                    .padding(8.dp)
                    .width(48.dp)
                    .height(48.dp)
            )
            Text(
                text = "Princeton University",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { Log.d("Test", "") },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            OutlinedButton(
                onClick = { Log.d("Test", "") },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                Text(
                    text = "Share Profile",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            IconButton (
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(50)
                    ),
                onClick = { Log.d("Test", "") },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun BookmarkedModules(
    modules: List<String> = List(4) { "Module ${it + 1}" },
    onNavigateToDetail: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Bookmarked modules",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            modules.forEach { _ ->
                ModuleItem(
                    onNavigateToDetail = {onNavigateToDetail()}
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    onNavigateToDetail: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                AuthorInfo()
            }
            item {
                BookmarkedModules(
                    onNavigateToDetail = {onNavigateToDetail}
                )
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    EduHubTheme {
        ProfileScreen(
            onNavigateToDetail = {}
        )
    }
}
