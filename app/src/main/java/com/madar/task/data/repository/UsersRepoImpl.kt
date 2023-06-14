package com.madar.task.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.madar.task.data.paging.UsersPagingSource
import com.madar.task.domain.model.User
import com.madar.task.domain.repository.UsersRepo
import com.madar.task.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepoImpl @Inject constructor() : UsersRepo {
    @Inject
    lateinit var usersPagingSource: UsersPagingSource
    override fun fetchUsers(): Flow<PagingData<User>> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)) {
            usersPagingSource
        }.flow
    }
}