package com.up.clinica_digital.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable function that displays the patient's chat screen interface.
 *
 * This screen includes a custom top bar with the recipient's name and a back button,
 * a message display area, and a bottom bar for message input and sending.
 *
 * @param name The name of the person being chatted with (e.g., the doctor).
 * @param onBack A callback function invoked when the back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPatient(
    name: String = "Roberto",
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .background(Color(0xFF0D47A1))
                )

                Surface(
                    tonalElevation = 0.dp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = Color(0xFF1565C0)
                            )
                        }

                        Text(
                            text = name,
                            modifier = Modifier
                                .weight(1f),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1565C0),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.width(48.dp))
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = DividerDefaults.color)
            }
        },
        bottomBar = {
            Divider(thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    tonalElevation = 1.dp,
                    color = Color(0xFFF0F0F0),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Dia 15/08 às 15:00 por favor! |",
                            color = Color(0xFF1565C0),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))


                Surface(
                    shape = CircleShape,
                    color = Color.Transparent,
                    modifier = Modifier.size(48.dp)
                ) {
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = Color(0xFF1565C0)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFAFAF9))
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1565C0),
                    tonalElevation = 2.dp,
                    modifier = Modifier.widthIn(max = 260.dp)
                ) {
                    Text(
                        text = "Olá, gostaria de agendar uma consulta.",
                        modifier = Modifier.padding(12.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = "Bom dia, para qual dia e horário?",
                    color = Color(0xFF1565C0),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
