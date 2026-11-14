package com.sugesw.ankerpods.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sugesw.ankerpods.data.settings.AppMode
import com.sugesw.ankerpods.ui.theme.AccentBrush
import com.sugesw.ankerpods.ui.theme.Black
import com.sugesw.ankerpods.ui.theme.CardColor
import com.sugesw.ankerpods.ui.theme.CardHigh
import com.sugesw.ankerpods.ui.theme.Cyan
import com.sugesw.ankerpods.ui.theme.TextLo
import com.sugesw.ankerpods.ui.theme.Violet
import com.sugesw.ankerpods.ui.theme.Warn

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Black)
            .drawBehind {
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(Violet.copy(alpha = 0.28f), Color.Transparent),
                        center = Offset(size.width * 0.5f, 0f),
                        radius = size.width * 1.1f
                    )
                )
            }
    ) { content() }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CardColor.copy(alpha = 0.92f))
            .border(1.dp, Color.White.copy(alpha = 0.07f), shape)
            .padding(padding),
        content = content
    )
}

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = TextLo,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        SectionLabel(title)
        GlassCard(content = content)
    }
}

@Composable
fun RowDivider() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.06f))
    )
}

@Composable
fun SoonChip(text: String = "Yakında") {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = Warn,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Warn.copy(alpha = 0.14f))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

@Composable
fun SwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit,
    soon: Boolean = false,
    enabled: Boolean = true
) {
    Row(
        Modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.45f)
            .clickable(enabled = enabled) { onChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                if (soon) {
                    Spacer(Modifier.width(8.dp))
                    SoonChip()
                }
            }
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextLo)
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = Cyan,
                uncheckedThumbColor = TextLo,
                uncheckedTrackColor = CardHigh,
                uncheckedBorderColor = Color.White.copy(alpha = 0.12f)
            )
        )
    }
}

@Composable
fun LinkRow(title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextLo)
        }
        Text("›", color = TextLo, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun GradientButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .height(52.dp)
            .clip(CircleShape)
            .background(AccentBrush)
            .clickable(onClick = onClick)
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GhostButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .height(44.dp)
            .clip(CircleShape)
            .background(CardHigh)
            .border(1.dp, Color.White.copy(alpha = 0.08f), CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PillChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .clip(CircleShape)
            .then(
                if (selected) Modifier.background(AccentBrush)
                else Modifier
                    .background(CardColor)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), CircleShape)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (selected) Color.White else TextLo,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ModeSelector(selected: AppMode, onSelect: (AppMode) -> Unit) {
    val shape = RoundedCornerShape(22.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CardColor)
            .border(1.dp, Color.White.copy(alpha = 0.07f), shape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AppMode.values().forEach { m ->
            val sel = m == selected
            Box(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .then(if (sel) Modifier.background(AccentBrush) else Modifier)
                    .clickable { onSelect(m) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    m.label,
                    color = if (sel) Color.White else TextLo,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/** Özgün, çizilmiş kulaklık görseli (üçüncü taraf görseli kullanılmaz). */
@Composable
fun HeadphoneHero(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val pulse by transition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseValue"
    )
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val glowCenter = Offset(w / 2f, h * 0.55f)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Cyan.copy(alpha = 0.22f * pulse),
                    Violet.copy(alpha = 0.10f * pulse),
                    Color.Transparent
                ),
                center = glowCenter,
                radius = w * 0.62f
            ),
            radius = w * 0.62f,
            center = glowCenter
        )
        val band = 14.dp.toPx()
        drawArc(
            brush = AccentBrush,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.18f, h * 0.06f),
            size = Size(w * 0.64f, h * 0.78f),
            style = Stroke(width = band, cap = StrokeCap.Round)
        )
        val cupW = w * 0.2f
        val cupH = h * 0.42f
        val cupTop = h * 0.43f
        val radius = CornerRadius(cupW * 0.5f)
        listOf(w * 0.18f - cupW / 2f, w * 0.82f - cupW / 2f).forEach { x ->
            drawRoundRect(
                color = CardHigh,
                topLeft = Offset(x, cupTop),
                size = Size(cupW, cupH),
                cornerRadius = radius
            )
            drawRoundRect(
                brush = AccentBrush,
                topLeft = Offset(x, cupTop),
                size = Size(cupW, cupH),
                cornerRadius = radius,
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}
