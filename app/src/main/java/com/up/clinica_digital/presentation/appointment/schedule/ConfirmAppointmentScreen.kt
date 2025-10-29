package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.appointment.components.DoctorInformation
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Tela de confirmação de agendamento.
 *
 * Esta tela exibe um resumo das informações da consulta (médico, data e hora)
 * que o paciente selecionou na tela anterior. Ela permite que o paciente
 * confirme o agendamento, que então é salvo no banco de dados.
 *
 * @param scheduleViewModel O ViewModel da tela anterior, reutilizado aqui
 * para carregar os dados do médico.
 * @param confirmViewModel O ViewModel desta tela, responsável por
 * salvar o agendamento.
 * @param navController Controlador de navegação.
 * @param doctorId O ID do médico (passado via navegação).
 * @param dateTime A data e hora selecionada (passada via navegação como String).
 */
@Composable
fun ConfirmAppointmentScreen(
    //parâmetros passados pela tela anterior AppointmentScheduleScreen
    scheduleViewModel: AppointmentScheduleViewModel = hiltViewModel(),
    confirmViewModel: ConfirmAppointmentViewModel = hiltViewModel(),
    navController: NavHostController,
    doctorId: String,
    dateTime: String
) {
    //estado da tela
    val scheduleUiState by scheduleViewModel.uiState.collectAsState()
    val confirmUiState by confirmViewModel.uiState.collectAsState()

    //data e hora definidos pela paciente na tela anterior
    val parsedDateTime = LocalDateTime.parse(dateTime)

    //carregando doutor
    LaunchedEffect(key1 = doctorId) {
        scheduleViewModel.loadDoctor(doctorId)
    }

    Scaffold(
        topBar = {
            //barra superior com opção de voltar a tela
            TopNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                confirmUiState.isLoading || scheduleUiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                confirmUiState.error != null -> Text("Erro: ${confirmUiState.error}", color = Color.Red)
                scheduleUiState.error != null -> Text("Erro: ${scheduleUiState.error}", color = Color.Red)
                confirmUiState.appointmentScheduled -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //Informações definidas pelo paciente na tela anterior
                        //que irá aparecer quando ele confirmar a consulta
                        //para o mesmo saber se vai ou não confirmar a consulta
                        Text("Consulta agendada com sucesso!", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            navController.navigate(BottomNavItem.Consultas.route) {
                                popUpTo(BottomNavItem.Medicos.route)
                            }
                        }) {
                            Text("Ver Minhas Consultas")
                        }
                    }
                }
                else -> {
                    //informações da tela anterior para paciente
                    //decidir se vai ou não agendar
                    Text(
                        text = "Confirmar Agendamento",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    //informações do doutor
                    scheduleUiState.doctor?.let { doctor ->
                        DoctorInformation(doctor = doctor)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Data e Hora:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        //data e hora do agendamento
                        Text(
                            text = parsedDateTime.format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                    FormatStyle.FULL,
                                    FormatStyle.SHORT
                                )
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        //botão para confirmar agendamento
                        Button(
                            onClick = {
                                confirmViewModel.scheduleAppointment(
                                    doctorId = doctorId,
                                    dateTime = parsedDateTime
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirmar")
                        }
                        // se as informações de médico não forem encontradas
                        // não é possivel agendar consulta
                    } ?: Text("Médico não encontrado.")
                }
            }
        }
    }
}