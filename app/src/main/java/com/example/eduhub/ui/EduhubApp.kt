package com.example.eduhub.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.eduhub.ui.theme.EduHubTheme

@Composable
fun EduhubApp(
    currentRoute: String,
    modifier: Modifier
) {
    EduHubTheme {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier = modifier
                ) {

                }
            }
        ) { }
    }
}