package com.madar.task.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madar.task.R
import com.madar.task.data.model.UserEntity
import com.madar.task.domain.repository.AddUserRepo
import com.madar.task.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val application: Application, private val repo: AddUserRepo
) : ViewModel() {


    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> get() = _errorState

    fun clearErrorState() {
        _errorState.value = ""
    }

    val name = MutableLiveData("")
    val nameError = MutableLiveData("")

    val age = MutableLiveData("")
    val ageError = MutableLiveData("")

    val jobTitle = MutableLiveData("")
    val jobTitleError = MutableLiveData("")

    val gender = MutableLiveData(true)/* male -> true, female -> false */

    private val _startInsertion = MutableStateFlow(false)
    val startInsertion: StateFlow<Boolean> get() = _startInsertion
    fun startInsertion(value: Boolean) {
        _startInsertion.value = value
    }

    private val _insertionState = MutableStateFlow(false)
    val insertionState: StateFlow<Boolean> get() = _insertionState
    fun insertionState(value: Boolean) {
        _insertionState.value = value
    }


    fun onInsertBtnClicked() {
        var pass = true

        if (!validateInput(input = name.value!!, observer = nameError, index = 0)) pass = false
        if (!validateInput(input = jobTitle.value!!, observer = jobTitleError, index = 1)) pass =
            false
        if (!validateInput(input = age.value!!, observer = ageError, index = 2)) pass = false

        if (pass) startInsertion(true)
    }

    fun insertUser() {
        viewModelScope.launch {
            val user = UserEntity(
                name = name.value!!,
                age = age.value!!.toInt(),
                jobTitle = jobTitle.value!!,
                gender = if (gender.value!!) 0 else 1
            )
            repo.insertUser(user).collectLatest { result ->
                startInsertion(false)
                when (result) {
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        _errorState.emit("")
                        insertionState(true)
                    }

                    is DataState.Error -> {
                        _errorState.emit(result.message!!)
                    }
                }
            }
        }
    }

    fun resetInputs() {
        name.value = ""
        jobTitle.value = ""
        age.value = ""
        gender.value = true
    }

    private fun validateInput(
        input: String, observer: MutableLiveData<String>, index: Int
    ): Boolean {
        if (input.isEmpty()) {
            observer.value = application.getString(R.string.empty_field_error_message)
            return false
        }

        when (index) {/* name validation */
            0 -> {
                if (input.length < 3) {
                    observer.value = application.getString(R.string.name_length_error_message)
                    return false
                }
                if (input.length > 20) {
                    observer.value = application.getString(R.string.name_length_error_message_1)
                    return false
                }
            }

            /* jobTitle validation */
            1 -> if (input.length < 3) {
                observer.value = application.getString(R.string.job_title_length_error_message)
                return false
            }

            /* Age validation */
            2 -> if (input.toInt() < 1) {
                observer.value = application.getString(R.string.age_length_error_message)
                return false
            }
        }

        return true
    }

}