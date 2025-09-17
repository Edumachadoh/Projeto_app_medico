package com.up.clinica_digital.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InitialScreen(
    onNavigateToCadastro: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bem-vindo", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onNavigateToCadastro, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Cadastrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Fazer Login")
            }
        }
    }
}
