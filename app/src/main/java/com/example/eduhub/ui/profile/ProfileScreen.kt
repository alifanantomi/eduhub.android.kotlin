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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7A
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.eduhub.ui.auth.login.LoginUIEvent
import com.example.eduhub.ui.components.AlertDialogConfirmation
import com.example.eduhub.ui.modules.list.ModuleItem
import com.example.eduhub.ui.modules.list.ModuleItemState
import com.example.eduhub.ui.theme.EduHubTheme
import com.example.eduhub.R

@Composable
fun AuthorInfo(
    id: String,
    name: String,
    image: String,
    role: String,
    handleLogout: () -> Unit,
    showLogoutDialog: Boolean,
    onLogoutDialogShowChange: (Boolean) -> Unit,
    dropdownExpanded: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    onDropdownExpandedChange: (Boolean) -> Unit
){
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (image != "") {
                AsyncImage(
                    model = image,
                    contentDescription = "Author image",
                    modifier = Modifier
                        .padding(8.dp)
                        .width(48.dp)
                        .height(48.dp)
                )
            } else {
                Icon(
                    painterResource(R.drawable.account_circle_fill),
                    contentDescription = "Author image",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            }
            Text(
                text = name,
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
                onClick = {  },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                Text(
                    text = "Share Profile",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Box {
                IconButton (
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(50)
                        ),
                    onClick = { onDropdownExpandedChange(!dropdownExpanded) },
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { onDropdownExpandedChange(false) }
                ) {
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = {  }
                    )
                    DropdownMenuItem(
                        text = { Text("Sign Out") },
                        onClick = {
                            onDropdownExpandedChange(false)
                            onLogoutDialogShowChange(true)
                        }
                    )
                }
            }
            if (showLogoutDialog) {
                AlertDialogConfirmation(
                    onDismissRequest = { onLogoutDialogShowChange(false) },
                    onConfirmation = {
                        onShowDialogChange(false)
                        handleLogout()
                    },
                    dialogTitle = "Sign Out",
                    dialogText = "Are you sure you want to sign out?",
                    icon = Icons.Default.Info
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
                    onNavigateToDetail = { onNavigateToDetail },
                    module = ModuleItemState(
                        title = "Module Title",
                        summary = "Module Summary",
                        image = ""
                    )
                )
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user

    var dropdownExpanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is LoginUIEvent.NavigateToLogin -> {
                    dropdownExpanded = false
                    showLogoutDialog = false
                    onNavigateToLogin()
                }
                else -> {}
            }
        }
    }

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
                AuthorInfo(
                    id = user?.id ?: "",
                    name = user?.name ?: "",
                    image = user?.image ?: "",
                    role = (user?.role ?: "USER").toString(),
                    dropdownExpanded = dropdownExpanded,
                    showLogoutDialog = showLogoutDialog,
                    onDropdownExpandedChange = { dropdownExpanded = it },
                    onLogoutDialogShowChange = { showLogoutDialog = it },
                    handleLogout = { viewModel.logout() },
                    onShowDialogChange = { showLogoutDialog = it }
                )
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
            onNavigateToDetail = {},
            onNavigateToLogin = {}
        )
    }
}
