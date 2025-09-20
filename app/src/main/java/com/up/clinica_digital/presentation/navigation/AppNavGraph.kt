package com.up.clinica_digital.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.up.clinica_digital.presentation.appointment.AppointmentScheduleScreen
import com.up.clinica_digital.presentation.auth.LoginScreen
import com.up.clinica_digital.presentation.auth.RegisterScreen
import com.up.clinica_digital.presentation.home.InitialScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.up.clinica_digital.domain.model.UserRole
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavConfig
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavigationBar

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
                onRegisterSuccess = { userId ->
                    navController.navigate(Screen.Home.createRoute(UserRole.PATIENT)) {
                        popUpTo(Screen.Initial.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Home.route) { backStackEntry ->
            val roleArg = backStackEntry.arguments?.getString("role")
            val role = UserRole.valueOf(roleArg ?: UserRole.PATIENT.name)

            LoggedInNavGraph(navController, role)
        }

        //a rota tem que receber {patientId}/{doctorId}
        composable(Screen.Appointment.route) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId")
            val patientId = backStackEntry.arguments?.getString("patientId")
            if (doctorId != null && patientId != null) {
                AppointmentScheduleScreen(
                    navController = navController,
                    doctorId = doctorId,
                    patientId = patientId
                )
            }
        }

    }
}