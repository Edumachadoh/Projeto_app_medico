package com.up.clinica_digital.presentation.appointment.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.clinica_digital.ui.theme.ClinicaDigitalTheme
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTimeDatePicker(
    onDateTimeSelected: (LocalDateTime) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute
    )

    var showTimePicker by remember { mutableStateOf(false) }
    var previewDateTime by remember { mutableStateOf<LocalDateTime?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(state = datePickerState)
        if (previewDateTime != null) {
            Text("Hora selecionada: ${previewDateTime}")
        }
        Button(onClick = { showTimePicker = true }) {
            Text("Selecionar horário")
        }

        Button(
            onClick = {
                val millis = datePickerState.selectedDateMillis
                val hour = timePickerState.hour
                val minute = timePickerState.minute
                val dateTime = millis?.let {
                    Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(hour, minute)
                }
                if (dateTime != null) {
                    previewDateTime = dateTime
                    onDateTimeSelected(dateTime)
                }
            }
        ) {
            Text("Confirmar")
        }
    }

    // TimePicker em janela suspensa
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancelar")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CalendarTimeDatePickerPreview() {
    ClinicaDigitalTheme {
        CalendarTimeDatePicker(onDateTimeSelected = {
            println("Selecionado: $it")
        })
    }
}
