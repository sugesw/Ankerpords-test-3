package com.sugesw.ankerpods.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Black = Color(0xFF000000)
val CardColor = Color(0xFF141418)
val CardHigh = Color(0xFF1E1E24)
val Cyan = Color(0xFF4DD2F0)
val Violet = Color(0xFF7C6CFF)
val Warn = Color(0xFFFFC857)
val Danger = Color(0xFFFF5C7A)
val TextHi = Color(0xFFF4F6F8)
val TextLo = Color(0xFF8E96A3)

val AccentBrush: Brush = Brush.linearGradient(listOf(Violet, Cyan))

private val Scheme = darkColorScheme(
    primary = Cyan,
    onPrimary = Color.Black,
    secondary = Violet,
    onSecondary = Color.White,
    tertiary = Warn,
    onTertiary = Color.Black,
    background = Black,
    onBackground = TextHi,
    surface = CardColor,
    onSurface = TextHi,
    surfaceVariant = CardHigh,
    onSurfaceVariant = TextLo,
    outline = Color(0x1FFFFFFF),
    error = Danger
)

private val AppTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp, letterSpacing = (-0.5).sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 17.sp),
    labelMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 11.sp, letterSpacing = 0.8.sp)
)

@Composable
fun AnkerPodsTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, typography = AppTypography, content = content)
}
