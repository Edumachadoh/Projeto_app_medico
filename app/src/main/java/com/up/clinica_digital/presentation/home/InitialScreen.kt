package com.up.clinica_digital.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
/**
 * The initial landing screen of the application when no user is logged in.
 *
 * It provides two main navigation options:
 * 1. Navigate to the registration screen.
 * 2. Navigate to the login screen.
 *
 * @param onNavigateToRegister A callback function to navigate to the [com.up.clinica_digital.presentation.auth.RegisterScreen].
 * @param onNavigateToLogin A callback function to navigate to the [com.up.clinica_digital.presentation.auth.LoginScreen].
 */
@Composable
fun InitialScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bem-vindo", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onNavigateToRegister, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Cadastrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Fazer Login")
            }
        }
    }
}
