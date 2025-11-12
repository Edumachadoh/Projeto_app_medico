package com.up.clinica_digital.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YoutubeVideoSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Vídeos de Saúde", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))

        val dengueVideo = "https://youtu.be/NWvkpEg1TN0?si=lKHTWkvkSnqESiNP"
        val covidVideo = "https://youtu.be/hLvQtAFkJY0?si=sjMlN-Wnl_RN1Fqi"

        YouTubeCard(title = "Sintomas da Dengue", videoUrl = dengueVideo)
        Spacer(Modifier.height(12.dp))
        YouTubeCard(title = "Prevenção da COVID-19", videoUrl = covidVideo)
    }
}

@Composable
fun YouTubeCard(title: String, videoUrl: String) {
    val embedUrl = videoUrl.replace("youtu.be/", "www.youtube.com/embed/")
        .substringBefore("?")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        AndroidView(
            factory = { context ->
                android.webkit.WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    loadData(
                        """
                        <html>
                          <body style="margin:0">
                            <iframe 
                              width="100%" height="100%" 
                              src="$embedUrl"
                              frameborder="0" allowfullscreen>
                            </iframe>
                          </body>
                        </html>
                        """.trimIndent(),
                        "text/html",
                        "utf-8"
                    )
                }
            }
        )
    }
    Text(title, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)
}