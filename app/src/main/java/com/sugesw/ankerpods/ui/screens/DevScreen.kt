package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.ui.GhostButton
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.SectionLabel
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.util.Hex

@Composable
fun DevScreen(onTestLog: () -> Unit) {
    var hex by remember { mutableStateOf("") }
    val parsed = remember(hex) { Hex.parse(hex) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Geliştirici", style = MaterialTheme.typography.headlineMedium)

        Column {
            SectionLabel("Ham komut (hex)")
            GlassCard(padding = 16.dp) {
                OutlinedTextField(
                    value = hex,
                    onValueChange = { hex = it },
                    label = { Text("örn. 01 02 03") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    when {
                        hex.isBlank() -> "Bayt yok."
                        parsed == null -> "Geçersiz hex."
                        else -> "${parsed.size} bayt: ${Hex.format(parsed)}"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = TextLo,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Gönderme, kulaklık bağlantısı kurulunca açılacak.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextLo
                )
            }
        }

        Column {
            SectionLabel("Test")
            GlassCard(padding = 16.dp) {
                GhostButton("Log'a deneme satırı yaz", onClick = onTestLog)
            }
        }
    }
}
