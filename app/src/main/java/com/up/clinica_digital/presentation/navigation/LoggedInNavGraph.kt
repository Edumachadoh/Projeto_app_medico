package com.up.clinica_digital.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.up.clinica_digital.domain.model.UserRole
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavConfig
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavigationBar


@Composable
fun LoggedInNavGraph(
    navController: NavHostController,
    userRole: UserRole
) {
    val items = remember { BottomNavConfig.itemsForRole(userRole) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: items.first().route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedRoute = currentRoute,
                onItemSelected = { item ->
                    navController.navigate(item.route) {
                        popUpTo(items.first().route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = items.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // TODO: adicionar rotas para cada tela do app!
            // Paciente
            composable(BottomNavItem.Medicos.route) { /* Lista de Médicos */ }
            composable(BottomNavItem.Agendar.route) { /* Agendar Screen */ }
            composable(BottomNavItem.Consultas.route) { /* Consultas Screen */ }
            composable(BottomNavItem.Perfil.route) { /* Perfil Screen */ }
            composable(BottomNavItem.Chat.route) { /* Chat Screen */ }

            // Médico
            composable(BottomNavItem.Agenda.route) { /* Agenda Screen */ }
            composable(BottomNavItem.Forum.route) { /* Fórum Screen */ }
        }
    }
}