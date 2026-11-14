package com.sugesw.ankerpods.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

object SystemSettings {

    fun open(context: Context, action: String, withPackage: Boolean = false) {
        val intent = Intent(action)
        if (withPackage) intent.data = Uri.parse("package:${context.packageName}")
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Bu ayar sayfası açılamadı", Toast.LENGTH_SHORT).show()
        }
    }

    fun bluetooth(c: Context) = open(c, Settings.ACTION_BLUETOOTH_SETTINGS)
    fun notificationAccess(c: Context) = open(c, Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    fun doNotDisturbAccess(c: Context) = open(c, Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
    fun batteryOptimization(c: Context) = open(c, Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
    fun writeSettings(c: Context) = open(c, Settings.ACTION_MANAGE_WRITE_SETTINGS, withPackage = true)
    fun appDetails(c: Context) = open(c, Settings.ACTION_APPLICATION_DETAILS_SETTINGS, withPackage = true)
}
