package com.up.clinica_digital.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.up.clinica_digital.domain.model.UserRole
import com.up.clinica_digital.presentation.appointment.doctor.AppointmentsAgendaScreen
import com.up.clinica_digital.presentation.appointment.doctor.details.AgendaDetailsScreen
import com.up.clinica_digital.presentation.appointment.patient.ScheduledAppointmentsScreen
import com.up.clinica_digital.presentation.appointment.patient.details.AppointmentDetailsScreen
import com.up.clinica_digital.presentation.appointment.schedule.AppointmentScheduleScreen
import com.up.clinica_digital.presentation.appointment.schedule.ConfirmAppointmentScreen
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavConfig
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavItem
import com.up.clinica_digital.presentation.component.bottom_nav.BottomNavigationBar
import com.up.clinica_digital.presentation.doctors.DoctorsListScreen
import com.up.clinica_digital.presentation.profile.ProfileScreen
import com.up.clinica_digital.presentation.chat.ChatPatient
import com.up.clinica_digital.presentation.doctors.DoctorDetailsScreen
import com.up.clinica_digital.presentation.forum.ForumScreen
import com.up.clinica_digital.presentation.forum.TopicItemScreen
import com.up.clinica_digital.presentation.termsOfUse.TermsOfUseScreen


@Composable
fun LoggedInNavGraph(
    parentNavController: NavHostController,
    userRole: UserRole
) {
    val bottomNavController = rememberNavController()
    val items = remember { BottomNavConfig.itemsForRole(userRole) }
    val navBackStackEntry = bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: items.first().route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedRoute = currentRoute,
                onItemSelected = { item ->
                    bottomNavController.navigate(item.route) {
                        popUpTo(items.first().route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = items.first().route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // TODO: adicionar rotas para cada tela do app!
            // -------------------------------------Paciente-------------------------------------

            //Lista de Médicos
            composable(BottomNavItem.Medicos.route) {
                DoctorsListScreen(navController = bottomNavController)
            }

            //Detalhes Médico
            composable(
                route = Screen.DoctorDetails.route,
                arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")
                if (doctorId != null) {
                    DoctorDetailsScreen(
                        navController = bottomNavController,
                        doctorId = doctorId
                    )
                }
            }

            // Agendamento
            composable(
                route = Screen.Appointment.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")

                if (doctorId != null) {
                    AppointmentScheduleScreen(
                        navController = bottomNavController,
                        doctorId = doctorId
                    )
                }
            }

            //Confirmar Agendamento Consulta
            composable(
                route = Screen.ConfirmAppointment.route,
                arguments = listOf(
                    navArgument("doctorId") { type = NavType.StringType },
                    navArgument("dateTime") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")!!
                val dateTime = backStackEntry.arguments?.getString("dateTime")!!

                ConfirmAppointmentScreen(
                    navController = bottomNavController,
                    doctorId = doctorId,
                    dateTime = dateTime
                )
            }

            //Lista de Consultas
            composable(BottomNavItem.Consultas.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ScheduledAppointmentsScreen(bottomNavController)
                }
            }

            //Detalhes consulta
            composable(
                route = Screen.AppointmentDetails.route,
                arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
            ) {
                AppointmentDetailsScreen(navController = bottomNavController)
            }

            //Perfil
            composable(BottomNavItem.Perfil.route) {
                ProfileScreen(
                    userRole = userRole
                )
            }

            //Chat
            composable(BottomNavItem.Chat.route) {
                ChatPatient(onBack = { parentNavController.popBackStack() })
            }


            // -------------------------------------Médico-------------------------------------

            //tela agenda
            composable(BottomNavItem.Agenda.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppointmentsAgendaScreen(bottomNavController)
                }
            }

            //Detalhes consulta
            composable(
                route = Screen.AgendaDetails.route,
                arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
            ) {
                AgendaDetailsScreen(navController = bottomNavController)
            }

            //tela forum
            composable(BottomNavItem.Forum.route) {
                ForumScreen(navController = bottomNavController)
            }

            //topico forum
            composable(
                route = Screen.TopicItem.route,
                arguments = listOf(navArgument("topicItemId") {type = NavType.StringType})
            ) { backStackEntry ->
                val topicId = backStackEntry.arguments?.getString("topicItemId")
                TopicItemScreen(
                    navController = bottomNavController,
                    topicId = topicId
                )
            }



        }
    }
}