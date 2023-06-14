package com.madar.task.data.paging

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.madar.task.R
import com.madar.task.data.db.UserDao
import com.madar.task.data.mapper.toUser
import com.madar.task.domain.model.User
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val application: Application,
    private val dao: UserDao
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 0

        return try {
            val entities = dao.fetchUsers(limit = params.loadSize, offset = page * params.loadSize)
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities.map { it.toUser() },
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.size < params.loadSize || entities.isEmpty()) null else page + 1
            )
        } catch (e: SQLiteException) {
            LoadResult.Error(Exception(application.getString(R.string.add_user_failure_message)))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}