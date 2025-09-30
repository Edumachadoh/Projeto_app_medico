// presentation/profile/ProfileScreen.kt
package com.up.clinica_digital.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.model.UserRole

@Composable
fun ProfileScreen(
    userRole: UserRole,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    LaunchedEffect(userRole) {
        viewModel.loadProfile(userRole == UserRole.DOCTOR)
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is ProfileUiState.Loading -> CircularProgressIndicator()
            is ProfileUiState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
            is ProfileUiState.Success -> {
                val user = state.user
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "CPF: ${user.cpf}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Informações específicas do perfil
                    when (user) {
                        is Doctor -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("CRM: ${user.crm} | UF: ${user.uf}")
                            Text("Especialização: ${user.specialization}")
                        }
                        is Patient -> {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Data de Nascimento: ${user.birthDate}")
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Text("Sair")
                    }
                }
            }
            else -> {}
        }
    }
}