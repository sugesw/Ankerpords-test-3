package com.sugesw.ankerpods.data.log

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class LogSource(val label: String) {
    BLUETOOTH("Bluetooth"),
    CALL("Arama"),
    NOTIFICATION("Bildirim"),
    RINGTONE("Zil"),
    SYSTEM("Sistem")
}

enum class LogResult(val label: String) {
    OK("başarılı"),
    FAIL("başarısız"),
    NO_REPLY("cevap yok"),
    PENDING("beklemede")
}

@Entity(tableName = "log_entries")
data class LogEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: Long,
    val source: String,
    val action: String,
    val result: String,
    val detail: String
)
