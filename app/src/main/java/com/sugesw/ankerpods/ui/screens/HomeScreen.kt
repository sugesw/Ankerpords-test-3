package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.settings.AppMode
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.ui.GradientButton
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.HeadphoneHero
import com.sugesw.ankerpods.ui.ModeSelector
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.Sub
import com.sugesw.ankerpods.ui.SoonChip
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.util.SystemSettings

@Composable
fun HomeScreen(settings: AppSettings, onMode: (AppMode) -> Unit, onOpen: (Sub) -> Unit) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Kulaklığım", style = MaterialTheme.typography.headlineMedium)

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadphoneHero(Modifier.fillMaxWidth().height(230.dp))
            Spacer(Modifier.height(8.dp))
            Text("R60i NC", style = MaterialTheme.typography.titleLarge)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Box(Modifier.size(8.dp).clip(CircleShape).background(TextLo))
                Spacer(Modifier.width(8.dp))
                Text("Bağlı değil", style = MaterialTheme.typography.bodyMedium, color = TextLo)
            }
            Spacer(Modifier.height(16.dp))
            GradientButton("Bluetooth Ayarlarını Aç", onClick = { SystemSettings.bluetooth(context) })
        }

        Column {
            SectionLabel("Mod")
            ModeSelector(settings.mode, onMode)
            Spacer(Modifier.height(10.dp))
            GlassCard(padding = 16.dp) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        settings.mode.label,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    SoonChip()
                }
                Spacer(Modifier.height(4.dp))
                Text(settings.mode.desc, style = MaterialTheme.typography.bodyMedium, color = TextLo)
            }
        }
        SettingsGroup("Özellikler") {
            Sub.values().forEachIndexed { i, sub ->
                if (i > 0) RowDivider()
                LinkRow(sub.title, sub.subtitle) { onOpen(sub) }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}
