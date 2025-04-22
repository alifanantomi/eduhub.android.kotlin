package com.example.eduhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eduhub.ui.auth.login.LoginScreen
import com.example.eduhub.ui.auth.register.RegisterScreen
import com.example.eduhub.ui.home.HomeScreen
import com.example.eduhub.ui.modules.list.ModuleListScreen
import com.example.eduhub.ui.profile.ProfileScreen

@Composable
fun EduHubNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
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
            HomeScreen()
        }

        composable(Destinations.MODULE_LIST_ROUTE) {
            ModuleListScreen()
        }

        composable(Destinations.PROFILE_ROUTE) {
            ProfileScreen()
        }
    }
}