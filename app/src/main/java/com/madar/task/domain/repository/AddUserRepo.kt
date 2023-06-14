package com.madar.task.domain.repository

import com.madar.task.data.model.UserEntity
import com.madar.task.domain.model.User
import com.madar.task.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AddUserRepo {
    suspend fun insertUser(user: UserEntity): Flow<DataState<Boolean>>
}