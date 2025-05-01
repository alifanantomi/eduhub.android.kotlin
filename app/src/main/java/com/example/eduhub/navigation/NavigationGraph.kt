package com.example.eduhub.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eduhub.data.local.preferences.UserPreferences
import com.example.eduhub.data.repository.AuthRepository
import com.example.eduhub.ui.auth.login.LoginScreen
import com.example.eduhub.ui.auth.register.RegisterScreen
import com.example.eduhub.ui.home.HomeScreen
import com.example.eduhub.ui.modules.detail.ModuleDetailScreen
import com.example.eduhub.ui.modules.list.ModuleListScreen
import com.example.eduhub.ui.profile.ProfileScreen
import com.example.eduhub.ui.splash.SplashScreen
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.eduhub.ui.modules.detail.ModuleDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EduHubNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    authRepository: AuthRepository,
    moduleDetailViewModel: ModuleDetailViewModel
) {
    val isLoggedIn by authRepository.isLoggedInFlow.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Destinations.HOME_ROUTE else Destinations.SPLASH_ROUTE,
        modifier = modifier
    ) {
        composable(Destinations.SPLASH_ROUTE) {
            SplashScreen(
                navigateToHome = { navController.navigate(Destinations.HOME_ROUTE) {
                    popUpTo(Destinations.SPLASH_ROUTE) { inclusive = true }
                } },
                navigateToLogin = { navController.navigate(Destinations.LOGIN_ROUTE) {
                    popUpTo(Destinations.SPLASH_ROUTE) { inclusive = true }
                } }
            )
        }

        composable(Destinations.LOGIN_ROUTE) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Destinations.REGISTER_ROUTER) },
                onNavigateToHome = {
                    navController.navigate(Destinations.HOME_ROUTE) {
                        popUpTo(Destinations.LOGIN_ROUTE) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN_ROUTE) }
            )
        }

        composable(Destinations.REGISTER_ROUTER) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN_ROUTE) }
            )
        }

        composable(Destinations.HOME_ROUTE) {
            HomeScreen(
                onNavigateToDetail = { moduleId -> navController.navigate("module/$moduleId") }
            )
        }

        composable(Destinations.MODULE_LIST_ROUTE) {
            ModuleListScreen(
                onNavigateToDetail = { moduleId -> navController.navigate("module/$moduleId") }
            )
        }

        composable(
            route = Destinations.MODULE_DETAIL_ROUTE,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: ""

            ModuleDetailScreen(
                moduleId = moduleId,
                scrollBehavior = scrollBehavior,
                viewModel = moduleDetailViewModel
            )
        }

        composable(Destinations.PROFILE_ROUTE) {
            ProfileScreen(
                onNavigateToDetail = { moduleId -> navController.navigate("module/$moduleId") },
                onNavigateToLogin = { navController.navigate(Destinations.LOGIN_ROUTE) {
                        popUpTo(Destinations.PROFILE_ROUTE) { inclusive = true }
                        popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
                        popUpTo(Destinations.MODULE_LIST_ROUTE) { inclusive = true }
                        popUpTo(Destinations.MODULE_DETAIL_ROUTE) { inclusive = true }
                    }
                }
            )
        }
    }
}