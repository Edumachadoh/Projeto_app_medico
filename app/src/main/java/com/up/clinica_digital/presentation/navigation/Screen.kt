package com.up.clinica_digital.presentation.navigation

import com.up.clinica_digital.domain.model.UserRole

sealed class Screen(val route: String) {
    object Initial : Screen("initial")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home/{role}") {
        fun createRoute(role: UserRole) = "home/${role.name}"
    }
}