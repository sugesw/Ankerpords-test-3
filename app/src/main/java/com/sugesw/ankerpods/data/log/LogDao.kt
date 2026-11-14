package com.sugesw.ankerpods.data.log

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insert(entry: LogEntry)

    @Query("SELECT * FROM log_entries ORDER BY id DESC")
    fun observeAll(): Flow<List<LogEntry>>

    @Query("DELETE FROM log_entries WHERE id NOT IN (SELECT id FROM log_entries ORDER BY id DESC LIMIT :keep)")
    suspend fun trim(keep: Int)

    @Query("DELETE FROM log_entries")
    suspend fun clear()
}
