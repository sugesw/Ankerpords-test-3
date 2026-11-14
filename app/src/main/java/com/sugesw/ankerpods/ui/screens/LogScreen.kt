package com.sugesw.ankerpods.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.log.LogEntry
import com.sugesw.ankerpods.data.log.LogRepository
import com.sugesw.ankerpods.data.log.LogResult
import com.sugesw.ankerpods.data.log.LogSource
import com.sugesw.ankerpods.ui.GhostButton
import com.sugesw.ankerpods.ui.GlassCard
import com.sugesw.ankerpods.ui.PillChip
import com.sugesw.ankerpods.ui.theme.Cyan
import com.sugesw.ankerpods.ui.theme.Danger
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.ui.theme.Violet
import com.sugesw.ankerpods.ui.theme.Warn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun sourceLabel(name: String) = LogSource.values().firstOrNull { it.name == name }?.label ?: name
private fun resultLabel(name: String) = LogResult.values().firstOrNull { it.name == name }?.label ?: name

private fun resultColor(name: String): Color = when (name) {
    "OK" -> Cyan
    "FAIL" -> Danger
    "NO_REPLY" -> Warn
    "PENDING" -> Violet
    else -> TextLo
}

@Composable
fun LogScreen(logs: List<LogEntry>, onClear: () -> Unit) {
    var filter by remember { mutableStateOf<LogSource?>(null) }
    val shown = remember(logs, filter) {
        val f = filter
        if (f == null) logs else logs.filter { it.source == f.name }
    }
    val clipboard = LocalClipboardManager.current
    val fmt = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 16.dp)) {
        Text("Log", style = MaterialTheme.typography.headlineMedium)
        Text(
            "${shown.size} kayıt · son ${LogRepository.MAX_ROWS} satır tutulur",
            style = MaterialTheme.typography.bodySmall,
            color = TextLo
        )
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillChip("Hepsi", filter == null) { filter = null }
            LogSource.values().forEach { s -> PillChip(s.label, filter == s) { filter = s } }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GhostButton("Kopyala", onClick = {
                val text = shown.joinToString("\n") {
                    "${fmt.format(Date(it.time))} [${sourceLabel(it.source)}] ${it.action} -> ${resultLabel(it.result)} ${it.detail}".trim()
                }
                clipboard.setText(AnnotatedString(text))
            })
            GhostButton("Temizle", onClick = onClear)
        }

        if (shown.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Henüz kayıt yok", color = TextLo)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 14.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shown, key = { it.id }) { e ->
                    GlassCard(padding = 14.dp) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(resultColor(e.result)))
                            Spacer(Modifier.width(10.dp))
                            Text(e.action, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            Text(fmt.format(Date(e.time)), style = MaterialTheme.typography.bodySmall, color = TextLo)
                        }
                        Text(
                            "${sourceLabel(e.source)} · ${resultLabel(e.result)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = resultColor(e.result),
                            modifier = Modifier.padding(start = 18.dp, top = 2.dp)
                        )
                        if (e.detail.isNotBlank()) {
                            Text(
                                e.detail,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextLo,
                                modifier = Modifier.padding(start = 18.dp, top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
