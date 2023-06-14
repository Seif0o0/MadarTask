package com.madar.task.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madar.task.utils.Constants.USER_TABLE

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @ColumnInfo(name = "user_name") val name: String,
    val age: Int,
    @ColumnInfo(name = "job_title") val jobTitle: String,
    val gender: Int /* 0-> male, 1-> female */
)