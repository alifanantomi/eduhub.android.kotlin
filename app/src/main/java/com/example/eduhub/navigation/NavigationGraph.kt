package com.example.eduhub.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eduhub.ui.auth.login.LoginScreen
import com.example.eduhub.ui.auth.register.RegisterScreen
import com.example.eduhub.ui.home.HomeScreen
import com.example.eduhub.ui.modules.detail.ModuleDetailScreen
import com.example.eduhub.ui.modules.list.ModuleListScreen
import com.example.eduhub.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EduHubNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN_ROUTE,
        modifier = modifier
    ) {
        composable(Destinations.LOGIN_ROUTE) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Destinations.REGISTER_ROUTER) },
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME_ROUTE) {
                        popUpTo(Destinations.LOGIN_ROUTE) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.REGISTER_ROUTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN_ROUTE) }
            )
        }

        composable(Destinations.HOME_ROUTE) {
            HomeScreen(
                onNavigateToDetail = { navController.navigate(Destinations.MODULE_DETAIL_ROUTE) }
            )
        }

        composable(Destinations.MODULE_LIST_ROUTE) {
            ModuleListScreen(
                onNavigateToDetail = { navController.navigate(Destinations.MODULE_DETAIL_ROUTE) }
            )
        }

        composable(Destinations.PROFILE_ROUTE) {
            ProfileScreen()
        }

        composable(Destinations.MODULE_DETAIL_ROUTE) {
            ModuleDetailScreen(
                scrollBehavior = scrollBehavior
            )
        }
    }
}