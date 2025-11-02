package com.up.clinica_digital.presentation.component.bottom_nav

import com.up.clinica_digital.domain.model.UserRole
/**
 * Configuration object for the Bottom Navigation Bar.
 *
 * This object provides logic to determine which navigation items
 * should be displayed based on the logged-in user's role.
 */
object BottomNavConfig {
    /**
     * Returns the list of [BottomNavItem] appropriate for the given [role].
     *
     * @param role The [UserRole] (PATIENT or DOCTOR) of the current user.
     * @return A list of [BottomNavItem] to be displayed in the navigation bar.
     */
    fun itemsForRole(role: UserRole): List<BottomNavItem> {
        return when (role) {
            UserRole.PATIENT -> listOf(
                BottomNavItem.Medicos,
                BottomNavItem.Consultas,
                BottomNavItem.Perfil,
                BottomNavItem.Chat,
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