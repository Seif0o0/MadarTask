package com.madar.task.domain.repository

import androidx.paging.PagingData
import com.madar.task.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepo {
    fun fetchUsers(): Flow<PagingData<User>>
}