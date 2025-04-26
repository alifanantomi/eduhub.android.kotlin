package com.example.eduhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.eduhub.ui.EduHubApp
import com.example.eduhub.ui.theme.EduHubTheme
import com.example.eduhub.ui.theme.PreviewWithTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduHubTheme {
                EduHubApp()
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    PreviewWithTheme {
        EduHubApp()
    }
}