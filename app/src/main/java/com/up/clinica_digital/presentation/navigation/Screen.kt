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
        fun createRoute(doctorId: String, dateTime: LocalDateTime?): String {
            return "confirm_appointment/$doctorId/$dateTime"
        }
    }

    object AppointmentDetails : Screen("appointment_details/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_details/$appointmentId"
    }

    object AgendaDetails : Screen("agenda_details/{appointmentId}") {
        fun createRoute(appointmentId: String) = "agenda_details/$appointmentId"
    }

    object TopicItem : Screen("forum_item/{topicItemId}"){
        fun createRoute(topicItemId: String) = "forum_item/$topicItemId"
    }

}
