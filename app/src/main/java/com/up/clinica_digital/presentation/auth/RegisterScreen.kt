package com.up.clinica_digital.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.up.clinica_digital.domain.model.Doctor
import com.up.clinica_digital.domain.model.Patient
import com.up.clinica_digital.domain.model.UserRole
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterSuccess: (String, UserRole) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.authState.collectAsState()

    var role by remember { mutableStateOf<UserRole?>(null) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ESPECÍFICO PARA PACIENTE
    var birthDate by remember { mutableStateOf("") }

    // ESPECÍFICOS PARA MÉDICO
    var crm by remember { mutableStateOf("") }
    var rqe by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Cadastro",
                style = MaterialTheme.typography.headlineMedium
            )
        })
    }) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {


            Spacer(Modifier.height(16.dp))

            if (role == null) {
                Text("Escolha seu perfil:")
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { role = UserRole.PATIENT }) { Text("Paciente") }
                    Button(onClick = { role = UserRole.DOCTOR }) { Text("Médico") }
                }
            } else {
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text("Nome") }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = cpf, onValueChange = { cpf = it },
                    label = { Text("CPF") }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                if (role == UserRole.PATIENT) {
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = birthDate, onValueChange = { birthDate = it },
                        label = { Text("Data de Nascimento (AAAA-MM-DD)") },
                        placeholder = { Text("1990-01-15") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (role == UserRole.DOCTOR) {
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = crm, onValueChange = { crm = it },
                        label = { Text("CRM") }, modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = rqe, onValueChange = { rqe = it },
                        label = { Text("RQE") }, modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = specialization, onValueChange = { specialization = it },
                        label = { Text("Especialização") }, modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = uf, onValueChange = { uf = it },
                        label = { Text("UF") }, modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (role == UserRole.PATIENT) {
                            if (birthDate.isEmpty()) {
                                return@Button
                            }

                            try {
                                val patient = Patient(
                                    id = UUID.randomUUID().toString(),
                                    name = name,
                                    email = email,
                                    cpf = cpf,
                                    passwordHash = password,
                                    birthDate = LocalDate.parse(birthDate)
                                )
                                viewModel.registerPatient(patient)
                            } catch (e: Exception) {
                                // TODO: Lidar com exceção de birth date inválida
                                return@Button
                            }
                        } else {
                            val doctor = Doctor(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                email = email,
                                cpf = cpf,
                                passwordHash = password,
                                crm = crm,
                                rqe = rqe,
                                specialization = specialization,
                                uf = uf
                            )
                            viewModel.registerDoctor(doctor)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar")
                }

                Spacer(Modifier.height(8.dp))

                TextButton(onClick = onNavigateToLogin) {
                    Text("Já tem conta? Faça login")
                }

                Spacer(Modifier.height(16.dp))

                when (uiState) {
                    is AuthUiState.Idle -> Unit
                    is AuthUiState.Loading -> CircularProgressIndicator()
                    is AuthUiState.Error -> {
                        val message = (uiState as AuthUiState.Error).message
                        Text(message, color = Color.Red)
                    }
                    is AuthUiState.Success -> {
                        val userId = (uiState as AuthUiState.Success).userId
                        val role = (uiState as AuthUiState.Success).role
                        LaunchedEffect(userId) {
                            onRegisterSuccess(userId, role)
                        }
                    }
                }
            }
        }
    }

}
