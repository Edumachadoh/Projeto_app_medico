package com.up.clinica_digital

import com.up.clinica_digital.data.AppDatabase
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.up.clinica_digital.controllers.PatientController
import com.up.clinica_digital.models.Doctor
import com.up.clinica_digital.models.ForumComment
import com.up.clinica_digital.models.ForumTopic
import com.up.clinica_digital.models.Patient
import com.up.clinica_digital.ui.theme.Clinica_digitalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var patientController: PatientController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "clinic_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        patientController = PatientController(db)

        val patientList = mutableStateListOf<Patient>()
        val topicList = mutableStateListOf<ForumTopic>()
        val commentList = mutableStateListOf<ForumComment>()
        val doctorList = mutableStateListOf<Doctor>()

        patientController.getPatients(patientList)

        val patient = Patient(
            id = "3",
            name = "Mariana Costa",
            email = "mariana@gmail.com",
            birthDate = "1992-08-15"
        )
        patientController.insertPatient(patient)

        setContent {
            Clinica_digitalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        PatientList(patientList)
                        ForumTopicList(topicList)
                        ForumCommentList(commentList)
                        DoctorList(doctorList)
                    }
                }
            }
        }
    }
}





@Composable
fun PatientList(patients: List<Patient>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Pacientes", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        for (patient in patients) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Nome: ${patient.name}")
                    Text("Email: ${patient.email}")
                    Text("Nascimento: ${patient.birthDate}")
                }
            }
        }
    }
}


@Composable
fun ForumTopicList(topics: List<ForumTopic>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Tópicos do Fórum", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        for (topic in topics) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Título: ${topic.title}")
                    Text("Conteúdo: ${topic.content}")
                    Text("Criado em: ${topic.createdAt}")
                }
            }
        }
    }
}

@Composable
fun ForumCommentList(comments: List<ForumComment>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Comentários do Fórum", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        for (comment in comments) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Codigo do tópico: ${comment.topicId}")
                    Text("Código do autor : ${comment.authorId}")
                    Text("Conteúdo: ${comment.content}")
                    Text("Criado em: ${comment.createdAt}")
                }
            }
        }
    }
}

@Composable
fun DoctorList(doctors: List<Doctor>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Médicos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        for (doctor in doctors) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Nome: ${doctor.name}")
                    Text("Email: ${doctor.email}")
                    Text("CRM: ${doctor.crm}")
                    Text("RQE: ${doctor.rqe}")
                    Text("Especialização: ${doctor.specialization}")
                }
            }
        }
    }
}
