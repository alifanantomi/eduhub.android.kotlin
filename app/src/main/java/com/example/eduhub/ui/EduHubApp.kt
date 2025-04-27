package com.example.eduhub.ui

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.eduhub.ui.snackbar.AppSnackbarController
import com.example.eduhub.ui.splash.SplashViewModel
import dagger.hilt.android.HiltAndroidApp

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EduHubApp(viewModel: SplashViewModel) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentRoute = currentDestination?.route ?: Destinations.MODULE_DETAIL_ROUTE

    val showBottomNav = when {
        currentRoute.startsWith("splash") -> false
        currentRoute.startsWith("login") -> false
        currentRoute.startsWith("register") -> false
        currentRoute.startsWith("forgot_password") -> false
        currentRoute.startsWith("module/{module}") -> false
        currentRoute.startsWith("admin/") -> false
        else -> true
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = AppSnackbarController.controller
    val snackbarMessage by snackbarController.snackbarMessage

    LaunchedEffect(viewModel.isLoggedIn) {
        if (viewModel.isLoggedIn == true) {
            navController.navigate(Destinations.HOME_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
            }
        } else {
            navController.navigate(Destinations.LOGIN_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

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

    val showTopBar = when {
        currentRoute.startsWith("module/{module}") -> true
        else -> false
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            val collapsedFraction = scrollBehavior.state.collapsedFraction
            val dynamicFontSize = lerp(24.sp, 18.sp, collapsedFraction)

            if (showTopBar) {
                LargeTopAppBar(
                    modifier = Modifier.padding(0.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(
                            text = "Computer Science: Programming with a Purpose",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = dynamicFontSize
                            ),
                            maxLines = if (collapsedFraction < 0.5f) 2 else 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {  }) {
                            BadgedBox(
                                badge = {
                                    Badge()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Bookmark"
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
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
        },
    ) {
        EduHubNavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            scrollBehavior = if (showTopBar) scrollBehavior else null
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, device = Devices.PIXEL_7A, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EduHubAppPreview() {
    EduHubApp(
        viewModel = TODO()
    )
}