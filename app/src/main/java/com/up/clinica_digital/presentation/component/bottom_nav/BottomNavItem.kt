package com.up.clinica_digital.presentation.component.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    // Paciente (ANA: sugestão, podem trocar, só conversem antes) ME AJUDE A ESCOLHER OS ICONS.
    object Medicos : BottomNavItem("medicos", Icons.AutoMirrored.Filled.List, "Médicos")
//    object Agendar : BottomNavItem("medicos", Icons.Default.Add, "Agendar")
    object Consultas : BottomNavItem("consultas", Icons.Default.DateRange, "Consultas")
    object Perfil : BottomNavItem("perfil", Icons.Default.Person, "Perfil")
    object Chat : BottomNavItem("chat", Icons.Default.Call, "Chat")

    // Médico (ANA: sugestão, podem trocar, só conversem antes) MIM AJUDE A ESCOLHER OS ICONS.
    object Agenda : BottomNavItem("agenda", Icons.Default.DateRange, "Agenda")
    object Forum : BottomNavItem("forum", Icons.Outlined.MailOutline, "Fórum")

    object TermsOfUse : BottomNavItem("terms_of_use", Icons.Outlined.MailOutline, "Termos")
}