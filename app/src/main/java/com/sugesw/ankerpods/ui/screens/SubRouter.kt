package com.sugesw.ankerpods.ui.screens

import androidx.compose.runtime.Composable
import com.sugesw.ankerpods.MainViewModel
import com.sugesw.ankerpods.data.settings.AppSettings
import com.sugesw.ankerpods.ui.Sub

@Composable
fun SubRouter(sub: Sub, settings: AppSettings, vm: MainViewModel, onBack: () -> Unit) {
    when (sub) {
        Sub.NOTIFICATIONS -> NotificationsScreen(settings, vm, onBack)
        Sub.CALLS -> CallsScreen(settings, vm, onBack)
        Sub.PRIORITY -> PriorityScreen(settings, vm, onBack)
        Sub.STATS -> StatsScreen(onBack)
        Sub.LOCATION -> LocationScreen(settings, vm, onBack)
        Sub.BATTERY -> BatteryScreen(onBack)
        Sub.CONTROLS -> ControlsScreen(onBack)
    }
}
