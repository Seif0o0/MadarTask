package com.madar.task.data.db

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.madar.task.data.model.UserEntity
import com.madar.task.utils.Constants.USER_TABLE
import kotlin.jvm.Throws

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE LIMIT :limit OFFSET :offset")
    suspend fun fetchUsers(limit: Int, offset: Int): List<UserEntity>
}