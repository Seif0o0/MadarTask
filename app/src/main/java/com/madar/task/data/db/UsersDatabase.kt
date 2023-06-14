package com.madar.task.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madar.task.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}