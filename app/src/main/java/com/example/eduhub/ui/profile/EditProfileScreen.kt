package com.example.eduhub.ui.profile

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.eduhub.R
import com.example.eduhub.ui.auth.login.LoginUIEvent
import com.example.eduhub.ui.snackbar.AppSnackbarController
import com.example.eduhub.ui.theme.EduHubTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackRoute: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        uri: Uri? ->
        uri?.let {
            viewModel.onImageSelected(it, context)
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            // Take a persist permission to access the file
            context.contentResolver.takePersistableUriPermission(
                it,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onImageSelected(it, context)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is ProfileUIEvent.ShowSnackbar -> {
                    AppSnackbarController.controller.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is ProfileUIEvent.NavigateToProfile -> {
                    onNavigateToProfile()
                }
                else -> {}
            }

        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                IconButton(onClick = { onBackRoute() }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left_long_line),
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    if (state.image.isNotEmpty()) {
                        AsyncImage(
                            model = state.image,
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .padding(8.dp)
                                .width(100.dp)
                                .height(100.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            painterResource(R.drawable.account_circle_fill),
                            contentDescription = "Author image",
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        )
                    }

                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { imagePicker.launch("image/*") }
                        ) {
                            Text(
                                text = "Gallery",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        TextButton(
                            onClick = { filePickerLauncher.launch(arrayOf("image/*")) }
                        ) {
                            Text(
                                text = "Files",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    TextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = state.email,
                        onValueChange = { },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    Button(
                        onClick = viewModel::onUpdateProfile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Save Profile",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

//                    state.error?.let {
//                        Text(text = it, color = Color.Red)
//                    }
                }
            }
        }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EditProfileScreenPreview() {
    EduHubTheme {
        EditProfileScreen(
            onBackRoute = {},
            onNavigateToProfile = {}
        )
    }
}