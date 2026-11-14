package com.sugesw.ankerpods.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.ui.theme.AccentBrush
import com.sugesw.ankerpods.ui.theme.CardColor
import com.sugesw.ankerpods.ui.theme.CardHigh
import com.sugesw.ankerpods.ui.theme.Cyan
import com.sugesw.ankerpods.ui.theme.TextHi
import com.sugesw.ankerpods.ui.theme.TextLo
import kotlin.math.roundToInt

@Composable
fun SubPage(
    title: String,
    onBack: () -> Unit,
    chip: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CardHigh)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = TextHi)
            }
            Spacer(Modifier.width(12.dp))
            Text(title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            if (chip != null) SoonChip(chip)
        }
        content()
    }
}

@Composable
fun SegmentedControl(
    options: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(22.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.45f)
            .clip(shape)
            .background(CardColor)
            .border(1.dp, Color.White.copy(alpha = 0.07f), shape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { i, label ->
            val sel = i == selected
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .then(if (sel) Modifier.background(AccentBrush) else Modifier)
                    .clickable(enabled = enabled) { onSelect(i) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    label,
                    color = if (sel) Color.White else TextLo,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun SliderRow(
    title: String,
    subtitle: String,
    value: Int,
    min: Int,
    max: Int,
    unit: String,
    onFinish: (Int) -> Unit,
    enabled: Boolean = true
) {
    var tmp by remember(value) { mutableFloatStateOf(value.toFloat()) }
    Column(
        Modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.45f)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
            Text("${tmp.roundToInt()} $unit", color = Cyan, style = MaterialTheme.typography.titleMedium)
        }
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextLo)
        Slider(
            value = tmp,
            onValueChange = { tmp = it.roundToInt().toFloat() },
            onValueChangeFinished = { onFinish(tmp.roundToInt()) },
            valueRange = min.toFloat()..max.toFloat(),
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = Cyan,
                activeTrackColor = Cyan,
                inactiveTrackColor = Color.White.copy(alpha = 0.12f)
            )
        )
    }
}

@Composable
fun TemplateField(label: String, value: String, onSave: (String) -> Unit) {
    var v by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = v,
        onValueChange = { v = it; onSave(it) },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)
    )
}

@Composable
fun SoundPickRow(title: String, subtitle: String, uri: String, onPick: (String) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { picked ->
        if (picked != null) {
            try {
                context.contentResolver.takePersistableUriPermission(picked, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (e: Exception) {
                // kalıcı izin verilemezse yine de yolu kaydet
            }
            onPick(picked.toString())
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { launcher.launch(arrayOf("audio/*")) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextLo)
        }
        Text(
            if (uri.isBlank()) "Seç" else "Seçildi",
            color = if (uri.isBlank()) TextLo else Cyan,
            style = MaterialTheme.typography.titleMedium
        )
        if (uri.isNotBlank()) {
            Spacer(Modifier.width(8.dp))
            Text("✕", color = TextLo, modifier = Modifier.clickable { onPick("") }.padding(6.dp))
        }
    }
}

@Composable
fun EmptyState(text: String, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier, padding = 20.dp) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextLo,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HintText(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodySmall,
        color = TextLo,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    )
}
