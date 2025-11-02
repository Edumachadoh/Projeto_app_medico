package com.up.clinica_digital.presentation.navigation

import com.up.clinica_digital.domain.model.UserRole
import java.time.LocalDateTime

/**
 * A sealed class representing all unique navigation destinations in the application.
 *
 * This class defines the routes and provides helper functions (`createRoute`)
 * to construct dynamic routes with arguments, ensuring type safety and
 * consistency when navigating.
 */
sealed class Screen(val route: String) {
    /**
     * The initial screen of the app, shown before login/register.
     */
    object Initial : Screen("initial")

    /**
     * The user login screen.
     */
    object Login : Screen("login")

    /**
     * The user registration screen.
     */
    object Register : Screen("register")

    /**
     * The main destination after login, which hosts the [LoggedInNavGraph].
     * Requires a [UserRole] argument.
     */
    object Home : Screen("home/{role}") {
        fun createRoute(role: UserRole) = "home/${role.name}"
    }

    /**
     * Patient: screen showing details for a specific doctor.
     * Requires a `doctorId` argument.
     */
    object DoctorDetails : Screen("doctor_details/{doctorId}") {
        fun createRoute(doctorId: String): String{
            return "doctor_details/$doctorId"
        }
    }

    /**
     * Patient: screen for scheduling an appointment with a specific doctor.
     * Requires a `doctorId` argument.
     */
    object Appointment : Screen("appointment_schedule/{doctorId}") {
        fun createRoute(doctorId: String): String {
            return "appointment_schedule/$doctorId"
        }
    }

    /**
     * Patient: screen to confirm the details of a new appointment.
     * Requires `doctorId` and `dateTime` arguments.
     */
    object ConfirmAppointment : Screen("confirm_appointment/{doctorId}/{dateTime}") {
        fun createRoute(doctorId: String, dateTime: LocalDateTime?): String {
            return "confirm_appointment/$doctorId/$dateTime"
        }
    }

    /**
     * Patient: screen showing details of a specific scheduled appointment.
     * Requires an `appointmentId` argument.
     */
    object AppointmentDetails : Screen("appointment_details/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_details/$appointmentId"
    }

    /**
     * Doctor: screen showing details of a specific appointment from their agenda.
     * Requires an `appointmentId` argument.
     */
    object AgendaDetails : Screen("agenda_details/{appointmentId}") {
        fun createRoute(appointmentId: String) = "agenda_details/$appointmentId"
    }

    /**
     * Doctor: screen showing the details and comments for a specific forum topic.
     * Requires a `topicItemId` argument.
     */
    object TopicItem : Screen("forum_item/{topicItemId}"){
        fun createRoute(topicItemId: String) = "forum_item/$topicItemId"
    }

    /**
     * A screen displaying the application's terms of use,
     * typically accessed from the [Register] screen.
     */
    object TermsOfUse : Screen("terms_of_use")
}