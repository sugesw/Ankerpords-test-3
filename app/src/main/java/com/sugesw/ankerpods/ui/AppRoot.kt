package com.sugesw.ankerpods.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.log.LogResult
import com.sugesw.ankerpods.data.log.LogSource
import com.sugesw.ankerpods.ui.screens.AboutScreen
import com.sugesw.ankerpods.ui.screens.DevScreen
import com.sugesw.ankerpods.ui.screens.HomeScreen
import com.sugesw.ankerpods.ui.screens.LogScreen
import com.sugesw.ankerpods.ui.screens.SettingsScreen
import com.sugesw.ankerpods.ui.screens.SubRouter
import com.sugesw.ankerpods.ui.theme.AccentBrush
import com.sugesw.ankerpods.ui.theme.CardColor
import com.sugesw.ankerpods.ui.theme.TextHi
import com.sugesw.ankerpods.ui.theme.TextLo

private enum class Tab(val title: String, val icon: ImageVector) {
    HOME("Ana", Icons.Filled.Home),
    LOG("Log", Icons.AutoMirrored.Filled.List),
    SETTINGS("Ayarlar", Icons.Filled.Settings),
    DEV("Geliştirici", Icons.Filled.Build),
    ABOUT("Hakkında", Icons.Filled.Info)
}

@Composable
fun AppRoot(vm: MainViewModel = viewModel()) {
    val settings by vm.settings.collectAsState()
    val logs by vm.logs.collectAsState()

    val tabs = Tab.values().filter { it != Tab.DEV || settings.developerMode }
    var selected by rememberSaveable { mutableStateOf(Tab.HOME.name) }
    val current = tabs.firstOrNull { it.name == selected } ?: Tab.HOME

    var sub by rememberSaveable { mutableStateOf<String?>(null) }
    val subScreen = Sub.values().firstOrNull { it.name == sub }
    BackHandler(enabled = subScreen != null) { sub = null }

    AppBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = TextHi,
            bottomBar = { FloatingNav(tabs, current) { selected = it.name; sub = null } }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                if (subScreen != null && current == Tab.HOME) {
                    SubRouter(subScreen, settings, vm) { sub = null }
                } else when (current) {
                    Tab.HOME -> HomeScreen(settings, vm::setMode) { sub = it.name }
                    Tab.LOG -> LogScreen(logs, vm::clearLogs)
                    Tab.SETTINGS -> SettingsScreen(settings, vm::setFlag, vm::setDuckMs)
                    Tab.DEV -> DevScreen {
                        vm.addLog(LogSource.SYSTEM, "Deneme satırı", LogResult.OK, "Geliştirici modu testi")
                    }
                    Tab.ABOUT -> AboutScreen(settings.developerMode, vm::setDeveloperMode)
                }
            }
        }
    }
}

@Composable
private fun FloatingNav(tabs: List<Tab>, current: Tab, onSelect: (Tab) -> Unit) {
    val shape = RoundedCornerShape(32.dp)
    Row(
        Modifier
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .fillMaxWidth()
            .clip(shape)
            .background(CardColor.copy(alpha = 0.96f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), shape)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { tab ->
            val sel = tab == current
            Box(
                Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .then(if (sel) Modifier.background(AccentBrush) else Modifier)
                    .clickable { onSelect(tab) },
                contentAlignment = Alignment.Center
            ) {
                Icon(tab.icon, contentDescription = tab.title, tint = if (sel) Color.White else TextLo)
            }
        }
    }
}
