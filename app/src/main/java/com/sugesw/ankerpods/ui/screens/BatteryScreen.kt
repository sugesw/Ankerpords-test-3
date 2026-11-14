package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.ui.EmptyState
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SoonChip
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.theme.TextLo

@Composable
fun BatteryScreen(onBack: () -> Unit) {
    SubPage("Pil ve bağlantı", onBack, chip = "Yakında") {
        Column {
            SectionLabel("Pil")
            GlassCard(padding = 16.dp) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Telefonun bildirdiği pil", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Text("—", style = MaterialTheme.typography.titleMedium, color = TextLo)
                }
                Row(
                    Modifier.fillMaxWidth().padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sol / sağ / kılıf pili", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    SoonChip("Kulaklık gerekli")
                }
            }
        }

        Column {
            SectionLabel("Bağlantı geçmişi")
            EmptyState("Kulaklığın bağlanma ve kopma olayları saatleriyle burada listelenecek.")
        }
    }
}
