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
/**
 * PEDRO:
 * Composable function for the Login Screen.
 *
 * This screen provides UI elements for user authentication, including
 * email and password fields, a login button, and a navigation option
 * to the registration screen. It observes the [AuthUiState] from the
 * [AuthViewModel] to handle loading, error, and success states.
 *
 * @param viewModel The [AuthViewModel] used for authentication logic.
 * @param onLoginSuccess A callback function that is invoked upon successful
 * login, providing the user's ID and role.
 * @param onNavigateToRegister A callback function to navigate to the
 * registration screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: (String, UserRole) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = {
        Text(
            "Login",
            style = MaterialTheme.typography.headlineMedium
        )})
    }) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


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

            // ANA: I think this here is pretty standard, in case you, pedro, are taking a look. I don't know if you need it
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
}