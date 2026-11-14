package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.data.settings.Flag
import com.sugesw.ankerpods.data.settings.NumKey
import com.sugesw.ankerpods.data.settings.TextKey
import com.sugesw.ankerpods.ui.HintText
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SegmentedControl
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.ui.TemplateField
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.util.SystemSettings

private val knownApps = listOf(
    "WhatsApp" to "com.whatsapp",
    "Telegram" to "org.telegram.messenger",
    "Instagram" to "com.instagram.android",
    "Discord" to "com.discord",
    "Messenger" to "com.facebook.orca",
    "Mesajlar" to "com.google.android.apps.messaging"
)

private val contentHints = listOf(
    "Yalnızca kimden geldiğini söyler.",
    "Gönderen ve mesajın kısa bir özetini okur.",
    "Mesajın tamamını okur."
)

@Composable
fun NotificationsScreen(settings: AppSettings, vm: MainViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    val contentMode = settings.num(NumKey.NOTIF_CONTENT).coerceIn(0, 2)

    SubPage("Bildirim okuma", onBack, chip = "Yakında") {
        SettingsGroup("Genel") {
            SwitchRow(
                title = "Bildirimleri sesli oku",
                subtitle = "Seçtiğin uygulamalardan gelen mesajları telefonun Türkçe sesiyle okur.",
                checked = settings.on(Flag.READ_NOTIFICATIONS),
                onChange = { vm.setFlag(Flag.READ_NOTIFICATIONS, it) }
            )
            RowDivider()
            SwitchRow(
                title = "Grup sohbetlerini atla",
                subtitle = "Grup mesajları okunmaz, yalnızca birebir yazışmalar okunur.",
                checked = settings.on(Flag.SKIP_GROUPS),
                onChange = { vm.setFlag(Flag.SKIP_GROUPS, it) }
            )
            RowDivider()
            SwitchRow(
                title = "Doğrulama kodlarını engelle",
                subtitle = "Banka, OTP ve doğrulama kodları içeren bildirimler asla sesli okunmaz.",
                checked = settings.on(Flag.BLOCK_OTP),
                onChange = { vm.setFlag(Flag.BLOCK_OTP, it) }
            )
        }

        Column {
            SectionLabel("Ne okunsun?")
            SegmentedControl(
                options = listOf("Gönderen", "Özet", "Tam metin"),
                selected = contentMode,
                onSelect = { vm.setNum(NumKey.NOTIF_CONTENT, it) }
            )
            Text(
                contentHints[contentMode],
                style = MaterialTheme.typography.bodySmall,
                color = TextLo,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
        }

        SettingsGroup("Cümle şablonu") {
            TemplateField("Mesaj anonsu", settings.text(TextKey.TPL_MESSAGE)) { vm.setText(TextKey.TPL_MESSAGE, it) }
            HintText("{kişi} gönderenin adıyla değişir. Örnek: Gelen mesaj: Ahmet")
        }

        SettingsGroup("Uygulamalar") {
            knownApps.forEachIndexed { i, (name, pkg) ->
                if (i > 0) RowDivider()
                SwitchRow(
                    title = name,
                    subtitle = "Bu uygulamanın bildirimleri okunsun.",
                    checked = pkg in settings.apps,
                    onChange = { vm.setApp(pkg, it) }
                )
            }
            RowDivider()
            LinkRow("Bildirim erişimi", "Okuma için bu izin verilmeli.") { SystemSettings.notificationAccess(context) }
        }
    }
}
