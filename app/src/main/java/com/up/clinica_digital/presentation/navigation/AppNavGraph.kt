package com.up.clinica_digital.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.up.clinica_digital.presentation.appointment.AppointmentScheduleScreen
import com.up.clinica_digital.presentation.auth.LoginScreen
import com.up.clinica_digital.presentation.auth.RegisterScreen
import com.up.clinica_digital.presentation.home.InitialScreen
import com.up.clinica_digital.domain.model.UserRole
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.up.clinica_digital.presentation.appointment.ConfirmAppointmentScreen

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

        composable(
            route = Screen.Appointment.route,
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("doctorId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
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
        composable(
            route = Screen.ConfirmAppointment.route,
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("doctorId") { type = NavType.StringType },
                navArgument("dateTime") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")!!
            val doctorId = backStackEntry.arguments?.getString("doctorId")!!
            val dateTime = backStackEntry.arguments?.getString("dateTime")!!

            ConfirmAppointmentScreen(
                navController = navController,
                doctorId = doctorId,
                patientId = patientId,
                dateTime = dateTime
            )
        }

    }
}