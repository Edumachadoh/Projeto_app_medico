package com.up.clinica_digital.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.up.clinica_digital.presentation.auth.LoginScreen
import com.up.clinica_digital.presentation.auth.RegisterScreen
import com.up.clinica_digital.presentation.home.InitialScreen

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
                onLoginSuccess = { userId ->
                    navController.navigate(Screen.Home.route) {
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
                onRegisterSuccess = { userId ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Initial.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Home.route) {
            // TODO: ir para a tela Home, após ser criada
            androidx.compose.material3.Text("Bem-vindo!")
        }
    }
}
