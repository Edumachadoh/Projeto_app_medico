package com.up.clinica_digital.presentation.appointment.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.up.clinica_digital.presentation.appointment.components.CalendarTimeDatePicker
import com.up.clinica_digital.presentation.appointment.components.DoctorInformation
import com.up.clinica_digital.presentation.component.top_nav.TopNavigationBar
import com.up.clinica_digital.presentation.navigation.Screen
import java.time.format.DateTimeFormatter

/*
*   Tela para agendamento de consulta
*   feita pelo paciente logado
*/
@Composable
fun AppointmentScheduleScreen(
    viewModel: AppointmentScheduleViewModel = hiltViewModel(),
    navController: NavHostController,
    doctorId: String,
) {
    //estado da tela (inicia como: Carregando; Erro; Sucesso)
    val uiState by viewModel.uiState.collectAsState()
    //formatando data para mandar parao banco
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    //carergando o doutor selecionado na tela listar médicos
    LaunchedEffect(key1 = doctorId) {
        viewModel.loadDoctor(doctorId)
    }

    Scaffold(
        topBar = {
            //barra de navegação com botão voltar
            TopNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.error != null -> Text(text = "Erro: ${uiState.error}", color = Color.Red)
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //estrutura da tela
                        Text(
                            text = "Agendamento",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        //Quando a tela carregar mostrará as informações
                        //do doutor selecionado
                        uiState.doctor?.let { doctor ->
                            DoctorInformation(doctor)
                            Spacer(modifier = Modifier.height(16.dp))
                            //Componente de calendário
                            // criado para selecionar
                            // data e hora da consulta
                            CalendarTimeDatePicker(
                                //irá registrar a data quando alterado
                                onDateTimeSelected = { dateTime ->
                                    viewModel.onDateTimeSelected(dateTime)
                                }
                            )
                            //irá mostrar a hora selecionada
                            //quando ela for modificada
                            uiState.selectedDateTime?.let {
                                Text(text = "Horário Selecionado: ${it.format(formatter)}")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            //Botão que leva para a tela confirmar agendamento
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Screen.ConfirmAppointment.createRoute(
                                            doctorId = uiState.doctor!!.id,
                                            dateTime = uiState.selectedDateTime
                                        )
                                    )
                                },
                                enabled = uiState.selectedDateTime != null
                            ) {
                                Text("Agendar")
                            }
                        } ?: run {
                            //se o médico não for encontrado
                            //não é possivel realizar agendamento
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Text("Médico não encontrado.")
                            }
                        }
                    }
                }
            }
        }
    }
}