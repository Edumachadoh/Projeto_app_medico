package com.up.clinica_digital.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.up.clinica_digital.presentation.component.YoutubeVideoSection
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    onNavigateToMedicos: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
) {
    val user by viewModel.user.collectAsState()
    val doctors by viewModel.doctors.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHomeData("Cardiologia")
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Olá, ${user?.name ?: "Usuário"}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Pesquisar") },
                singleLine = true
            )

            Text(
                text = "Agende sua consulta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val specialties = listOf("Cardiologia", "Psicologia", "Dermatologia", "Neurologia", "Geral", "Pediatria")
                items(specialties.size) { index ->
                    EspecialidadeCard(
                        nome = specialties[index],
                        onClick = { viewModel.loadHomeData(specialties[index]) }
                    )
                }
            }

//            YoutubeVideoSection()

            Text(
                text = "Especialistas em destaque",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(doctors) { doctor ->
                    // ANA: Rating mockado!
                    val randomRating = remember { (4..5).random() + (0..9).random() / 10.0 }
                    EspecialistaCard(nome = doctor.name, rating = String.format(Locale.US, "%.1f", randomRating))
                }
            }
        }
    }
}

@Composable
fun EspecialidadeCard(nome: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(100.dp, 100.dp)
            .clickable { onClick() },
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(nome, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun EspecialistaCard(nome: String, rating: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.size(width = 160.dp, height = 120.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.height(8.dp))
            Text(nome, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("⭐ $rating", fontSize = 12.sp, color = Color.Gray)
        }
    }
}