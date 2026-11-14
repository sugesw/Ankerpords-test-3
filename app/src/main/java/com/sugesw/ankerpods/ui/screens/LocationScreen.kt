package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.data.settings.Flag
import com.sugesw.ankerpods.ui.EmptyState
import com.sugesw.ankerpods.ui.HintText
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.util.SystemSettings

@Composable
fun LocationScreen(settings: AppSettings, vm: MainViewModel, onBack: () -> Unit) {
    val context = LocalContext.current

    SubPage("Son konum", onBack, chip = "Yakında") {
        Column {
            SectionLabel("Son bilinen yer")
            EmptyState("Kulaklık bağlantısı koptuğunda telefonun yaklaşık konumu, saat ve kısa bir not burada görünecek.")
        }

        SettingsGroup("Kayıt") {
            SwitchRow(
                title = "Kopunca konumu kaydet",
                subtitle = "Bağlantı koptuğunda yaklaşık konumu ve saati kaydeder.",
                checked = settings.on(Flag.SAVE_LOCATION),
                onChange = { vm.setFlag(Flag.SAVE_LOCATION, it) }
            )
            RowDivider()
            LinkRow("Konum izni", "Arka planda çalışması için \"her zaman izin ver\" gerekir.") { SystemSettings.appDetails(context) }
            HintText("Bu gerçek bir takip değil, yalnızca \"son bilinen yer\" kaydıdır. Arka plan konum izni pili biraz daha fazla kullanır.")
        }
    }
}
