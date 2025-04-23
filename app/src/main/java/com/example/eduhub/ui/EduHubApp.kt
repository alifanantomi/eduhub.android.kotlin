package com.example.eduhub.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eduhub.navigation.EduHubNavHost
import androidx.compose.runtime.getValue
import com.example.eduhub.navigation.Destinations
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.eduhub.ui.snackbar.AppSnackbarController
import com.example.eduhub.ui.snackbar.SnackbarController

@Composable
fun EduHubApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentRoute = currentDestination?.route ?: Destinations.LOGIN_ROUTE

    val showBottomNav = when {
        currentRoute.startsWith("login") -> false
        currentRoute.startsWith("register") -> false
        currentRoute.startsWith("forgot_password") -> false
        currentRoute.startsWith("admin/") -> false
        else -> true
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = AppSnackbarController.controller
    val snackbarMessage by snackbarController.snackbarMessage

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            val result = snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                duration = message.duration
            )

            if (result == SnackbarResult.ActionPerformed) {
                message.onAction?.invoke()
            }

            snackbarController.snackbarShown()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        snackbarData.visuals.actionLabel?.let { actionLabel ->
                            TextButton(
                                onClick = { snackbarData.performAction() },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.inversePrimary
                                )
                            ) {
                                Text(text = actionLabel)
                            }
                        }
                    }
                ) {
                    Text(text = snackbarData.visuals.message)
                }
            }
        },
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == Destinations.HOME_ROUTE,
                        onClick = {
                            navController.navigate(Destinations.HOME_ROUTE) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Email, contentDescription = "Modules") },
                        label = { Text("Modules", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == Destinations.MODULE_LIST_ROUTE,
                        onClick = {
                            navController.navigate(Destinations.MODULE_LIST_ROUTE) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                        label = { Text("Profile", style = MaterialTheme.typography.labelSmall) },
                        selected = currentRoute == Destinations.PROFILE_ROUTE,
                        onClick = {
                            navController.navigate(Destinations.PROFILE_ROUTE) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        EduHubNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}