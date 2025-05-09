package com.example.eduhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.eduhub.data.repository.AuthRepository
import com.example.eduhub.ui.EduHubApp
import com.example.eduhub.ui.splash.SplashViewModel
import com.example.eduhub.ui.theme.EduHubTheme
import com.example.eduhub.ui.theme.PreviewWithTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading
        }

        setContent {
            EduHubTheme {
                EduHubApp(
                    viewModel = viewModel,
                    authRepository = authRepository
                )
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    PreviewWithTheme {
        EduHubApp(
            viewModel = SplashViewModel(
                userPreferences = TODO()
            ),
            authRepository = TODO()
        )
    }
}