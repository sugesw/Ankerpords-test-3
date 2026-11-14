package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.ui.EmptyState
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.theme.AccentBrush
import com.sugesw.ankerpods.ui.theme.TextLo

private val days = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz")

@Composable
fun StatsScreen(onBack: () -> Unit) {
    // Veri bağlanınca 0f..1f arası değerler gelecek.
    val values = List(7) { 0f }

    SubPage("Dinleme istatistiği", onBack, chip = "Yakında") {
        GlassCard(padding = 20.dp) {
            Text("BU HAFTA", style = MaterialTheme.typography.labelMedium, color = TextLo)
            Text("0 sa 0 dk", style = MaterialTheme.typography.headlineMedium)
            Text("Geçen haftaya göre: —", style = MaterialTheme.typography.bodyMedium, color = TextLo)
        }

        Column {
            SectionLabel("Gün gün")
            GlassCard(padding = 20.dp) {
                Row(
                    Modifier.fillMaxWidth().height(140.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    days.forEachIndexed { i, d ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                Modifier
                                    .width(20.dp)
                                    .height((6f + 100f * values[i]).dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .then(
                                        if (values[i] > 0f) Modifier.background(AccentBrush)
                                        else Modifier.background(Color.White.copy(alpha = 0.12f))
                                    )
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(d, style = MaterialTheme.typography.bodySmall, color = TextLo)
                        }
                    }
                }
            }
        }

        Column {
            SectionLabel("En çok dinlenenler")
            EmptyState("Müzik dinledikçe burada görünecek. 30 saniyeden kısa dinlemeler sayılmaz.")
        }

        Text(
            "Veriler yalnızca telefonunda saklanır, dışarı gönderilmez.",
            style = MaterialTheme.typography.bodySmall,
            color = TextLo,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
