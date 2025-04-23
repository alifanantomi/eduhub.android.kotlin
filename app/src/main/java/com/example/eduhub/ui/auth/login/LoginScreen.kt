package com.example.eduhub.ui.auth.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eduhub.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface

    val state = viewModel.state

    if (viewModel.navigateToHome) {
        viewModel.onNavigatedToHome()

        onNavigateToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(surface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Sign in to EduHub",
                    style = MaterialTheme.typography.headlineMedium,
                    color = onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "using your EduHub account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Enter your Email") },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Unspecified),
                        readOnly = state.isLoading,
                        textStyle = MaterialTheme.typography.labelLarge
                    )
                    TextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Password") },
                        maxLines = 1,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = state.isLoading,
                        textStyle = MaterialTheme.typography.labelLarge
                    )
                }

                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = viewModel::onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        enabled = !state.isLoading,
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Continue")
                        }
                    }

                    state.error?.let {
                        Text(text = it, color = Color.Red)
                    }

                    TextButton(
                        onClick = { Log.d("Forgot", "") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = !state.isLoading
                    ) {
                        Text("Forgot Password?")
                    }

                    OutlinedButton(
                        onClick = { onNavigateToRegister() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !state.isLoading
                    ) {
                        Text("Create an Account")
                    }

                    Text("or", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { Log.d("Test", "") },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            enabled = !state.isLoading
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google_logo),
                                contentDescription = "Google",
                                modifier = Modifier.size(32.dp),
                                tint = Color.Unspecified
                            )
                        }

                        OutlinedButton(
                            onClick = { Log.d("Test", "") },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            enabled = !state.isLoading
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_github_logo),
                                contentDescription = "GitHub",
                                modifier = Modifier.size(32.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }

    }

}