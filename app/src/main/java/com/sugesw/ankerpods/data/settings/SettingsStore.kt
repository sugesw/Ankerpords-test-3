package com.sugesw.ankerpods.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class AppMode(val label: String, val desc: String) {
    NORMAL(
        "Normal",
        "Günlük kullanım. Seçtiğin uygulamaların bildirimleri ve gelen aramalar tam cümleyle anons edilir."
    ),
    SPOR(
        "Spor",
        "Koşu, spor salonu gibi anlar için. Anonslar kısa tutulur ve yalnızca öncelikli kişiler okunur."
    ),
    SESSIZ(
        "Sessiz",
        "Uyku ve toplantı saatleri için. Anons yapılmaz; sadece çok önemli (Seviye 1) kişiler duyulur."
    )
}

enum class Flag(val key: String, val default: Boolean) {
    READ_NOTIFICATIONS("read_notifications", true),
    SKIP_GROUPS("skip_groups", true),
    BLOCK_OTP("block_otp", true),
    ANNOUNCE_CALLS("announce_calls", true),
    MASK_LOGS("mask_logs", true),
    AUTO_ANSWER_L1("auto_answer_l1", false),
    L1_EXEMPT("l1_exempt", true),
    MISSED_TOTAL("missed_total", true),
    USE_STARRED("use_starred", true),
    SAVE_LOCATION("save_location", false)
}

enum class TextKey(val key: String, val default: String) {
    TPL_MESSAGE("tpl_message", "Gelen mesaj: {kişi}"),
    TPL_NORMAL("tpl_normal", "Gelen bağlantı: {kişi}"),
    TPL_LEVEL1("tpl_level1", "Gelen bağlantı: {kişi}"),
    TPL_UNKNOWN("tpl_unknown", "Gelen bağlantı: bilinmeyen kaynak"),
    TPL_REPEAT("tpl_repeat", "Tekrar bağlantı: {kişi}"),
    TPL_SUMMARY("tpl_summary", "{sayı} cevapsız bağlantı"),
    SND_CONNECT("snd_connect", ""),
    SND_HANGUP("snd_hangup", ""),
    SND_RING("snd_ring", "")
}

enum class NumKey(val key: String, val default: Int) {
    REPEAT_SEC("repeat_sec", 9),
    L1_WAIT_SEC("l1_wait_sec", 3),
    SILENCE_MIN("silence_min", 10),
    NOTIF_CONTENT("notif_content", 0)
}

data class AppSettings(
    val developerMode: Boolean = false,
    val mode: AppMode = AppMode.NORMAL,
    val duckMs: Int = 1500,
    val flags: Map<Flag, Boolean> = emptyMap(),
    val texts: Map<TextKey, String> = emptyMap(),
    val nums: Map<NumKey, Int> = emptyMap(),
    val apps: Set<String> = emptySet()
) {
    fun on(flag: Flag): Boolean = flags[flag] ?: flag.default
    fun text(key: TextKey): String = texts[key] ?: key.default
    fun num(key: NumKey): Int = nums[key] ?: key.default
}

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsStore(private val context: Context) {

    private object Keys {
        val DEV = booleanPreferencesKey("developer_mode")
        val MODE = stringPreferencesKey("mode")
        val DUCK = intPreferencesKey("duck_ms")
        val APPS = stringSetPreferencesKey("notif_apps")
    }

    val flow: Flow<AppSettings> = context.dataStore.data.map { p ->
        AppSettings(
            developerMode = p[Keys.DEV] ?: false,
            mode = AppMode.values().firstOrNull { it.name == p[Keys.MODE] } ?: AppMode.NORMAL,
            duckMs = p[Keys.DUCK] ?: 1500,
            flags = Flag.values().associateWith { f -> p[booleanPreferencesKey(f.key)] ?: f.default },
            texts = TextKey.values().associateWith { t -> p[stringPreferencesKey(t.key)] ?: t.default },
            nums = NumKey.values().associateWith { n -> p[intPreferencesKey(n.key)] ?: n.default },
            apps = p[Keys.APPS] ?: emptySet()
        )
    }

    suspend fun setDeveloperMode(value: Boolean) { context.dataStore.edit { it[Keys.DEV] = value } }
    suspend fun setMode(mode: AppMode) { context.dataStore.edit { it[Keys.MODE] = mode.name } }
    suspend fun setDuckMs(ms: Int) { context.dataStore.edit { it[Keys.DUCK] = ms } }
    suspend fun setFlag(flag: Flag, value: Boolean) { context.dataStore.edit { it[booleanPreferencesKey(flag.key)] = value } }
    suspend fun setText(key: TextKey, value: String) { context.dataStore.edit { it[stringPreferencesKey(key.key)] = value } }
    suspend fun setNum(key: NumKey, value: Int) { context.dataStore.edit { it[intPreferencesKey(key.key)] = value } }
    suspend fun setApp(pkg: String, on: Boolean) {
        context.dataStore.edit {
            val cur = it[Keys.APPS] ?: emptySet()
            it[Keys.APPS] = if (on) cur + pkg else cur - pkg
        }
    }
}
