package com.sugesw.ankerpods

import android.app.Application
import com.sugesw.ankerpods.data.log.AppDatabase
import com.sugesw.ankerpods.data.log.LogRepository
import com.sugesw.ankerpods.data.settings.SettingsStore

class AnkerPodsApp : Application() {
    val database by lazy { AppDatabase.build(this) }
    val logRepo by lazy { LogRepository(database.logDao()) }
    val settings by lazy { SettingsStore(this) }
}
