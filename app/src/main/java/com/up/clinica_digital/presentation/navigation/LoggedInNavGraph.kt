package com.up.clinica_digital.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.up.clinica_digital.domain.model.UserRole
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavConfig
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavigationBar


@Composable
fun LoggedInNavGraph(
    parentNavController: NavHostController,
    userRole: UserRole
) {
    val bottomNavController = rememberNavController()
    val items = remember { BottomNavConfig.itemsForRole(userRole) }
    val navBackStackEntry = bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: items.first().route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedRoute = currentRoute,
                onItemSelected = { item ->
                    bottomNavController.navigate(item.route) {
                        popUpTo(items.first().route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = items.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // TODO: adicionar rotas para cada tela do app!
            // Paciente
            composable(BottomNavItem.Medicos.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Lista de Médicos")
                }
            }
            composable(BottomNavItem.Agendar.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Agendar Screen")
                }
            }
            composable(BottomNavItem.Consultas.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Consultas Screen")
                }
            }
            composable(BottomNavItem.Perfil.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Perfil Screen")
                }
            }
            composable(BottomNavItem.Chat.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Chat Screen")
                }
            }

            // Médico
            composable(BottomNavItem.Agenda.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Agenda Screen")
                }
            }
            composable(BottomNavItem.Forum.route) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    androidx.compose.material3.Text("Fórum Screen")
                }
            }
        }
    }
}