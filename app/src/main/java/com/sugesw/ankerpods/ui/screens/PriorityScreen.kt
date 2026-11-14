package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.data.settings.Flag
import com.sugesw.ankerpods.ui.EmptyState
import com.sugesw.ankerpods.ui.GhostButton
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.ui.theme.Cyan
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.ui.theme.Violet
import com.sugesw.ankerpods.ui.theme.Warn
import com.sugesw.ankerpods.util.SystemSettings

@Composable
private fun LevelCard(badge: String, title: String, desc: String, color: androidx.compose.ui.graphics.Color) {
    GlassCard(padding = 16.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(badge, color = color, style = MaterialTheme.typography.labelMedium)
            Text("  $title", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(4.dp))
        Text(desc, style = MaterialTheme.typography.bodySmall, color = TextLo)
    }
}

@Composable
fun PriorityScreen(settings: AppSettings, vm: MainViewModel, onBack: () -> Unit) {
    val context = LocalContext.current

    SubPage("Öncelikli kişiler", onBack, chip = "Yakında") {
        Column {
            SectionLabel("Seviyeler")
            Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)) {
                LevelCard(
                    "SEVİYE 1", "Çok önemli",
                    "Spor ve sessiz modda da çalışır. Müzik kısılır, isim söylenir, kısa bir süre zil çalar. İstersen arama otomatik açılır.",
                    Cyan
                )
                LevelCard(
                    "SEVİYE 2", "Normal",
                    "Standart akış: çağrı sesi, isim anonsu ve arama sürerse aralıklı kısa tekrar.",
                    Violet
                )
                LevelCard(
                    "SEVİYE 3", "Sessiz",
                    "Spor modunda anons yapılmaz; kişi yalnızca cevapsız arama özetinde görünür.",
                    Warn
                )
            }
        }

        SettingsGroup("Liste kaynağı") {
            SwitchRow(
                title = "Rehberde yıldızlı kişileri kullan",
                subtitle = "Rehberinde yıldızladığın kişiler öncelikli sayılır.",
                checked = settings.on(Flag.USE_STARRED),
                onChange = { vm.setFlag(Flag.USE_STARRED, it) }
            )
        }

        Column {
            SectionLabel("Kişilerim")
            EmptyState("Henüz öncelikli kişi eklenmedi. Kişi eklemek için önce rehber izni verilmeli.")
            Spacer(Modifier.height(10.dp))
            GhostButton("Rehber iznini ver", onClick = { SystemSettings.appDetails(context) })
        }
    }
}
