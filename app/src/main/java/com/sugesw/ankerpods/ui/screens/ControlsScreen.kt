package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SegmentedControl
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.ui.theme.TextLo
import androidx.compose.ui.unit.dp

@Composable
fun ControlsScreen(onBack: () -> Unit) {
    SubPage("Kulaklık kontrolü", onBack, chip = "Kulaklık gerekli") {
        GlassCard(padding = 16.dp) {
            Text("Kulaklık bağlı değil", style = MaterialTheme.typography.titleMedium)
            Text(
                "Bu kontroller kulaklığı aldıktan sonra denenip doğrulanınca açılacak.",
                style = MaterialTheme.typography.bodySmall,
                color = TextLo
            )
        }

        Column {
            SectionLabel("Gürültü kontrolü")
            SegmentedControl(listOf("Kapalı", "ANC", "Şeffaf"), selected = 1, onSelect = {}, enabled = false)
        }

        Column {
            SectionLabel("ANC seviyesi")
            SegmentedControl(listOf("Hafif", "Orta", "Güçlü"), selected = 1, onSelect = {}, enabled = false)
        }

        SettingsGroup("Ses ve bağlantı") {
            Column(Modifier.alpha(0.45f)) {
                LinkRow("Ekolayzır", "Ses dengesini ayarla.") { }
            }
            RowDivider()
            SwitchRow(
                title = "Oyun modu",
                subtitle = "Ses gecikmesini azaltır.",
                checked = false, onChange = {}, enabled = false
            )
            RowDivider()
            SwitchRow(
                title = "Uzamsal ses",
                subtitle = "Sesi etrafında çevreleyen bir his verir.",
                checked = false, onChange = {}, enabled = false
            )
            RowDivider()
            SwitchRow(
                title = "Çoklu bağlantı",
                subtitle = "İki cihaza aynı anda bağlanır.",
                checked = false, onChange = {}, enabled = false
            )
        }
    }
}
