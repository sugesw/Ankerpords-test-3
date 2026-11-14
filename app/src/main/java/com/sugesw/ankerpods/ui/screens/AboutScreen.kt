package com.sugesw.ankerpods.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.Branding
import com.sugesw.ankerpods.BuildConfig
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.GradientButton
import com.sugesw.ankerpods.ui.HeadphoneHero
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.theme.AccentBrush
import com.sugesw.ankerpods.ui.theme.TextLo

@Composable
fun AboutScreen(developerMode: Boolean, onToggleDeveloper: (Boolean) -> Unit) {
    val context = LocalContext.current
    var taps by remember { mutableIntStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Hakkında", style = MaterialTheme.typography.headlineMedium)

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeadphoneHero(Modifier.fillMaxWidth().height(150.dp))
            Text(
                Branding.APP_NAME,
                style = MaterialTheme.typography.headlineMedium.copy(brush = AccentBrush)
            )
            Text(
                "v${BuildConfig.VERSION_NAME}",
                style = MaterialTheme.typography.bodyMedium,
                color = TextLo,
                modifier = Modifier
                    .clickable {
                        taps += 1
                        if (taps >= 7) {
                            taps = 0
                            val next = !developerMode
                            onToggleDeveloper(next)
                            Toast.makeText(
                                context,
                                if (next) "Geliştirici modu açıldı" else "Geliştirici modu kapandı",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .padding(8.dp)
            )
        }

        Column {
            SectionLabel("Geliştirici")
            GlassCard(padding = 16.dp) {
                Text(Branding.DEVELOPER, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                GradientButton(
                    text = Branding.LINK_LABEL,
                    onClick = {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Branding.LINK_URL)))
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, "Bağlantı açılamadı", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            "Soundcore ve Anker, ilgili sahiplerinin ticari markalarıdır. Bu uygulama resmî değildir, kişisel kullanım içindir.",
            style = MaterialTheme.typography.bodySmall,
            color = TextLo
        )
    }
}
