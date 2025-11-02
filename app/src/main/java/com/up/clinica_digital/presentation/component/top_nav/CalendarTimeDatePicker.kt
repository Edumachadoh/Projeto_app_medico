package com.up.clinica_digital.presentation.component.top_nav

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

/**
 * A composable component used in the [com.up.clinica_digital.presentation.appointment.schedule.AppointmentScheduleScreen]
 * that allows the patient to select a date and time for their appointment.
 *
 * @param onDateTimeSelected A callback function that is invoked when the
 * patient confirms a date and time selection.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTimeDatePicker(
    onDateTimeSelected: (LocalDateTime) -> Unit,
) {
    //PEDRO: State that stores the picked date
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    //PEDRO: State that stores the picked time
    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = true
    )

    //PEDRO: These variables control whether the time picker dialog is shown after a date is selected
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(state = datePickerState)
        Button(onClick = {
            datePickerState.selectedDateMillis?.let {
                selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                showTimePicker = true
            }
        }) {
            Text("Selecionar horário")
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedDate?.let {
                        val dateTime = it.atTime(timePickerState.hour, timePickerState.minute)
                        onDateTimeSelected(dateTime)
                    }
                    showTimePicker = false
                }) {
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