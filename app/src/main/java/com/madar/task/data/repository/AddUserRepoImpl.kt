package com.madar.task.data.repository

import android.app.Application
import android.database.sqlite.SQLiteException
import com.madar.task.R
import com.madar.task.data.db.UserDao
import com.madar.task.data.model.UserEntity
import com.madar.task.domain.model.User
import com.madar.task.domain.repository.AddUserRepo
import com.madar.task.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUserRepoImpl @Inject constructor(
    private val application: Application,
    private val dao: UserDao
) : AddUserRepo {
    override suspend fun insertUser(user: UserEntity): Flow<DataState<Boolean>> {
        return flow {
            try {
                emit(DataState.Loading())
                dao.insertUser(user)
                emit(DataState.Success(data = true))
            } catch (e: SQLiteException) {
                emit(DataState.Error(message = application.getString(R.string.add_user_failure_message)))
            }
        }
    }
}