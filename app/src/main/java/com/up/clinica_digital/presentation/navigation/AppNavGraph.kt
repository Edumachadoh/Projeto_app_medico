package com.up.clinica_digital.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.up.clinica_digital.presentation.auth.LoginScreen
import com.up.clinica_digital.presentation.auth.RegisterScreen
import com.up.clinica_digital.presentation.home.InitialScreen
import com.up.clinica_digital.domain.model.UserRole
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.up.clinica_digital.presentation.termsOfUse.TermsOfUseScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Initial.route
    ) {
        composable(Screen.Initial.route) {
            InitialScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { userId, role ->
                    navController.navigate(Screen.Home.createRoute(role)) {
                        popUpTo(Screen.Initial.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { userId, role ->
                    navController.navigate(Screen.Home.createRoute(role)) {
                        popUpTo(Screen.Initial.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument("role") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val roleArg = backStackEntry.arguments?.getString("role")
            val role = UserRole.valueOf(roleArg ?: UserRole.PATIENT.name)

            LoggedInNavGraph(navController, role)
        }

        // termos de uso
        // composable(route = "terms_of_use_route") {
        //     TermsOfUseScreen(navController = navController)
        // }

    }
}