package com.example.eduhub.ui.auth.register

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eduhub.R
import com.example.eduhub.ui.snackbar.AppSnackbarController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val background = MaterialTheme.colorScheme.background
    val surface = MaterialTheme.colorScheme.surface
    val primary = MaterialTheme.colorScheme.primary
    val onSurface = MaterialTheme.colorScheme.onSurface

    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is RegisterUIEvent.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                is RegisterUIEvent.ShowSnackbar -> {
                    AppSnackbarController.controller.showSnackbar(
                        message = event.message
                    )
                }

            }
        }
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
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Create an EduHub account",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = onSurface,
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
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text("Full name") },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Unspecified),
                        readOnly = state.isLoading
                    )
                    TextField(
                        value = state.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Email") },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Unspecified),
                        readOnly = state.isLoading
                    )
                    TextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Password") },
                        maxLines = 1,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = state.isLoading
                    )
                }

                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("By creating account, you accept EduHub's ")
                            withStyle(
                                SpanStyle(
                                    color = primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("Terms of Use ")
                            }
                            append("and acknowledge the ")
                            withStyle(
                                SpanStyle(
                                    color = primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("Privacy notes")
                            }
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Button(
                        onClick = viewModel::onRegisterClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
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


                    Text("or create an account with", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),

                        ) {
                        OutlinedButton(
                            onClick = { Log.d("Test", "") },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 8.dp)
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
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_github_logo),
                                contentDescription = "GitHub",
                                modifier = Modifier.size(32.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Already have an account?")
                        TextButton(
                            onClick = { onNavigateToLogin() },
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Text("Sign In")
                        }
                    }
                }
            }
        }

    }

}