package com.up.clinica_digital.presentation.navigation

sealed class Screen(val route: String) {
    object Initial : Screen("initial")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home") // TODO: alterar para a própria Home quando for criada
}
