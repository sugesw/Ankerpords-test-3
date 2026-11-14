package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.data.settings.Flag
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SoonChip
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.ui.theme.Cyan
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.util.SystemSettings
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onFlag: (Flag, Boolean) -> Unit,
    onDuckMs: (Int) -> Unit
) {
    val context = LocalContext.current
    var tmp by remember(settings.duckMs) { mutableFloatStateOf(settings.duckMs.toFloat()) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Ayarlar", style = MaterialTheme.typography.headlineMedium)

        SettingsGroup("Ses") {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Müzik kısma hızı", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.width(8.dp))
                    SoonChip()
                    Spacer(Modifier.weight(1f))
                    Text("${"%.1f".format(tmp / 1000f)} sn", color = Cyan, style = MaterialTheme.typography.titleMedium)
                }
                Text(
                    "Anons başlarken müziğin ne kadar sürede kısılacağı ve bitince ne kadar sürede eski seviyesine döneceği.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextLo
                )
                Slider(
                    value = tmp,
                    onValueChange = { tmp = (it / 100f).roundToInt() * 100f },
                    onValueChangeFinished = { onDuckMs(tmp.roundToInt()) },
                    valueRange = 500f..3000f,
                    colors = SliderDefaults.colors(
                        thumbColor = Cyan,
                        activeTrackColor = Cyan,
                        inactiveTrackColor = Color.White.copy(alpha = 0.12f)
                    )
                )
            }
        }

        SettingsGroup("İzinler") {
            LinkRow("Bildirim erişimi", "Bildirimleri okuyabilmek için gerekli.") { SystemSettings.notificationAccess(context) }
            RowDivider()
            LinkRow("Rahatsız Etme erişimi", "Aramayı susturabilmek için gerekli.") { SystemSettings.doNotDisturbAccess(context) }
            RowDivider()
            LinkRow("Pil kısıtlaması", "Uygulamanın arka planda kapanmaması için kısıtlamayı kaldır.") { SystemSettings.batteryOptimization(context) }
            RowDivider()
            LinkRow("Bluetooth", "Kulaklık eşleştirme ve bağlantı ayarları.") { SystemSettings.bluetooth(context) }
            RowDivider()
            LinkRow("Zil sesini değiştirme", "Kulaklık bağlanınca zil sesini değiştirebilmek için gerekli.") { SystemSettings.writeSettings(context) }
            RowDivider()
            LinkRow("Uygulama izinleri", "Rehber, telefon ve konum izinleri.") { SystemSettings.appDetails(context) }
        }

        SettingsGroup("Gizlilik") {
            SwitchRow(
                title = "Kişisel verileri kayıtlarda gizle",
                subtitle = "Numara ve mesaj metinleri Log'da maskelenir. Kayıtlar yalnızca telefonda kalır.",
                checked = settings.on(Flag.MASK_LOGS),
                onChange = { onFlag(Flag.MASK_LOGS, it) },
                soon = true
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}
