package com.up.clinica_digital.presentation.navigation

import com.up.clinica_digital.domain.model.UserRole
import java.time.LocalDateTime

sealed class Screen(val route: String) {
    object Initial : Screen("initial")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home/{role}") {
        fun createRoute(role: UserRole) = "home/${role.name}"
    }

    object DoctorsList : Screen("doctors_list_screen")
    object DoctorDetails : Screen("doctor_details/{doctorId}") {
        fun createRoute(doctorId: String): String{
            return "doctor_details/$doctorId"
        }
    }
    object Appointment : Screen("appointment_schedule/{doctorId}") {
        fun createRoute(doctorId: String): String {
            return "appointment_schedule/$doctorId"
        }
    }

    object ConfirmAppointment : Screen("confirm_appointment/{doctorId}/{dateTime}") {
        fun createRoute( doctorId: String, dateTime: LocalDateTime): String {
            return "confirm_appointment/$doctorId/$dateTime"
        }
    }
}
