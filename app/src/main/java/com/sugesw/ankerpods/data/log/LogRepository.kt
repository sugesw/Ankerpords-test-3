package com.sugesw.ankerpods.data.log

import kotlinx.coroutines.flow.Flow

class LogRepository(private val dao: LogDao) {

    fun observe(): Flow<List<LogEntry>> = dao.observeAll()

    suspend fun add(source: LogSource, action: String, result: LogResult, detail: String = "") {
        dao.insert(
            LogEntry(
                time = System.currentTimeMillis(),
                source = source.name,
                action = action,
                result = result.name,
                detail = detail
            )
        )
        dao.trim(MAX_ROWS)
    }

    suspend fun clear() = dao.clear()

    companion object {
        const val MAX_ROWS = 1000
    }
}
