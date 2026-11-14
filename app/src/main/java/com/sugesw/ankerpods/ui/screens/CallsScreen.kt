package com.sugesw.ankerpods.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.data.settings.Flag
import com.sugesw.ankerpods.data.settings.NumKey
import com.sugesw.ankerpods.data.settings.TextKey
import com.sugesw.ankerpods.ui.HintText
import com.sugesw.ankerpods.ui.LinkRow
import com.sugesw.ankerpods.ui.RowDivider
import com.sugesw.ankerpods.ui.SettingsGroup
import com.sugesw.ankerpods.ui.SliderRow
import com.sugesw.ankerpods.ui.SoundPickRow
import com.sugesw.ankerpods.ui.SubPage
import com.sugesw.ankerpods.ui.SwitchRow
import com.sugesw.ankerpods.ui.TemplateField
import com.sugesw.ankerpods.util.SystemSettings

@Composable
fun CallsScreen(settings: AppSettings, vm: MainViewModel, onBack: () -> Unit) {
    val context = LocalContext.current

    SubPage("Arama anonsu", onBack, chip = "Yakında") {
        SettingsGroup("Genel") {
            SwitchRow(
                title = "Arayanı anons et",
                subtitle = "Gelen aramada arayanın adını söyler. Müzik yavaşça kısılır, anons bitince geri döner.",
                checked = settings.on(Flag.ANNOUNCE_CALLS),
                onChange = { vm.setFlag(Flag.ANNOUNCE_CALLS, it) }
            )
        }

        SettingsGroup("Anons metinleri") {
            TemplateField("Normal arama", settings.text(TextKey.TPL_NORMAL)) { vm.setText(TextKey.TPL_NORMAL, it) }
            TemplateField("Çok önemli kişi (Seviye 1)", settings.text(TextKey.TPL_LEVEL1)) { vm.setText(TextKey.TPL_LEVEL1, it) }
            TemplateField("Bilinmeyen numara", settings.text(TextKey.TPL_UNKNOWN)) { vm.setText(TextKey.TPL_UNKNOWN, it) }
            TemplateField("Tekrar arayan", settings.text(TextKey.TPL_REPEAT)) { vm.setText(TextKey.TPL_REPEAT, it) }
            TemplateField("Cevapsız özeti", settings.text(TextKey.TPL_SUMMARY)) { vm.setText(TextKey.TPL_SUMMARY, it) }
            HintText("{kişi} arayanın adıyla, {sayı} cevapsız arama sayısıyla değişir. Her kişi için ayrı okunuş (telaffuz) sonra eklenecek.")
        }

        SettingsGroup("Anons akışı") {
            SliderRow(
                title = "Tekrar aralığı",
                subtitle = "Arama çalmaya devam ederse isim kaç saniyede bir tekrar söylenir.",
                value = settings.num(NumKey.REPEAT_SEC), min = 5, max = 20, unit = "sn",
                onFinish = { vm.setNum(NumKey.REPEAT_SEC, it) }
            )
            RowDivider()
            SliderRow(
                title = "Seviye 1 bekleme süresi",
                subtitle = "Çok önemli kişide isim söylendikten sonra zilin kaç saniye çalacağı.",
                value = settings.num(NumKey.L1_WAIT_SEC), min = 1, max = 8, unit = "sn",
                onFinish = { vm.setNum(NumKey.L1_WAIT_SEC, it) }
            )
            RowDivider()
            SwitchRow(
                title = "Seviye 1'de otomatik aç",
                subtitle = "Bekleme süresi bitince arama kendiliğinden açılır. Varsayılan kapalı; kapalıyken zil ve isim tekrarı normal sürer.",
                checked = settings.on(Flag.AUTO_ANSWER_L1),
                onChange = { vm.setFlag(Flag.AUTO_ANSWER_L1, it) }
            )
        }

        SettingsGroup("Sustur") {
            HintText("Arama reddedilmez, karşı tarafa \"reddedildi\" bilgisi gitmez. Uygulamanın sesleri susar ve müzik eski seviyesine döner.")
            SliderRow(
                title = "Geçici sessiz süre",
                subtitle = "Susturulan kişi bu süre boyunca tekrar aradığında ses çıkmaz, müzik kısılmaz.",
                value = settings.num(NumKey.SILENCE_MIN), min = 1, max = 60, unit = "dk",
                onFinish = { vm.setNum(NumKey.SILENCE_MIN, it) }
            )
            RowDivider()
            SwitchRow(
                title = "Seviye 1 kişiler susturmadan muaf",
                subtitle = "Çok önemli kişiler susturulsa bile tekrar aradığında anons edilir.",
                checked = settings.on(Flag.L1_EXEMPT),
                onChange = { vm.setFlag(Flag.L1_EXEMPT, it) }
            )
            RowDivider()
            SwitchRow(
                title = "Cevapsız özetinde toplam sayıyı söyle",
                subtitle = "Açıkken \"3 cevapsız bağlantı: Ahmet\" gibi toplam sayı söylenir; kapalıyken yalnızca son arama.",
                checked = settings.on(Flag.MISSED_TOTAL),
                onChange = { vm.setFlag(Flag.MISSED_TOTAL, it) }
            )
        }

        SettingsGroup("Sesler") {
            SoundPickRow(
                title = "Bağlandı sesi",
                subtitle = "Aramayı cevaplayınca çalan kısa ses.",
                uri = settings.text(TextKey.SND_CONNECT),
                onPick = { vm.setText(TextKey.SND_CONNECT, it) }
            )
            RowDivider()
            SoundPickRow(
                title = "Kapanma sesi",
                subtitle = "Görüşme bitince çalan kısa ses. Cevapsız ve susturulan aramalarda çalmaz.",
                uri = settings.text(TextKey.SND_HANGUP),
                onPick = { vm.setText(TextKey.SND_HANGUP, it) }
            )
            RowDivider()
            SoundPickRow(
                title = "Kulaklıkta zil sesi",
                subtitle = "Kulaklık bağlanınca bu zil sesi atanır, kopunca eskisi geri gelir.",
                uri = settings.text(TextKey.SND_RING),
                onPick = { vm.setText(TextKey.SND_RING, it) }
            )
            HintText("Ses dosyalarını sen seçersin, uygulamanın içinde hazır ses yoktur.")
        }

        SettingsGroup("İzinler") {
            LinkRow("Telefon ve rehber", "Arayanı tanımak ve aramayı yönetmek için.") { SystemSettings.appDetails(context) }
            RowDivider()
            LinkRow("Rahatsız Etme erişimi", "Zili susturabilmek için.") { SystemSettings.doNotDisturbAccess(context) }
        }
    }
}
