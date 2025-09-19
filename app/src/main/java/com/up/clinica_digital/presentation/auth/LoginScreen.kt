package com.up.clinica_digital.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.up.clinica_digital.domain.model.UserRole

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: (String, UserRole) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Ainda não tem conta? Cadastre-se")
        }

        Spacer(Modifier.height(16.dp))

        // ANA: acho isso daqui meio padrão, caso você, pedroca, estiver dando uma olhada. sei lá se você precisar
        when (uiState) {
            is AuthUiState.Idle -> Unit
            is AuthUiState.Loading -> CircularProgressIndicator()
            is AuthUiState.Error -> {
                val message = (uiState as AuthUiState.Error).message
                Text(message, color = Color.Red)
            }
            is AuthUiState.Success -> {
                val successState = uiState as AuthUiState.Success
                LaunchedEffect(successState.userId) {
                    onLoginSuccess(successState.userId, successState.role)
                }
            }
        }
    }
}