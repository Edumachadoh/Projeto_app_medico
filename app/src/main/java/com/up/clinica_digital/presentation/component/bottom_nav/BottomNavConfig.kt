package com.up.clinica_digital.presentation.component.bottom_nav

import com.up.clinica_digital.domain.model.UserRole

object BottomNavConfig {
    fun itemsForRole(role: UserRole): List<BottomNavItem> {
        return when (role) {
            UserRole.PATIENT -> listOf(
                BottomNavItem.Medicos,
                BottomNavItem.Consultas,
                BottomNavItem.Perfil,
                BottomNavItem.Chat
            )
            UserRole.DOCTOR -> listOf(
                BottomNavItem.Agenda,
                BottomNavItem.Perfil,
                BottomNavItem.Chat,
                BottomNavItem.Forum
            )
        }
    }
}