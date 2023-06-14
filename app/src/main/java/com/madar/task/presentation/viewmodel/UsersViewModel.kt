package com.madar.task.presentation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.madar.task.data.db.UserDao
import com.madar.task.domain.repository.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repo: UsersRepo
) : ViewModel() {

    val users = repo.fetchUsers().cachedIn(viewModelScope)

    private val _emptyState = MutableStateFlow(View.GONE)
    val emptyState: StateFlow<Int> get() = _emptyState

    fun handleLoadState(combinedLoadStates: CombinedLoadStates, itemCount: Int) {
        if(combinedLoadStates.refresh is LoadState.NotLoading){
            _emptyState.value = if(itemCount == 0) View.VISIBLE
            else View.GONE
        }
    }
}