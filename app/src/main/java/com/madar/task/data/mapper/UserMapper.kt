package com.madar.task.data.mapper

import com.madar.task.data.model.UserEntity
import com.madar.task.domain.model.User

fun UserEntity.toUser() = User(
    userId = userId,
    name = name,
    age = age,
    jobTitle = jobTitle,
    gender = gender,
)