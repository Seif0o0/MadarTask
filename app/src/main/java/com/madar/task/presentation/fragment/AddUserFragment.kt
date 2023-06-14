package com.madar.task.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.madar.task.R
import com.madar.task.databinding.FragmentAddUserBinding
import com.madar.task.presentation.viewmodel.AddUserViewModel
import com.madar.task.utils.CustomDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUserFragment : Fragment() {
    private val viewModel: AddUserViewModel by viewModels()
    private lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.usersButton.setOnClickListener {
            findNavController().navigate(
                AddUserFragmentDirections.actionAddUserFragmentToUsersFragment()
            )
        }

        binding.addButton.setOnClickListener {
            viewModel.onInsertBtnClicked()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.startInsertion.collectLatest {
                    if (it) {
                        viewModel.insertUser()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.insertionState.collectLatest {
                    if (it) {
                        viewModel.insertionState(false)
                        CustomDialog.showSuccessDialog(
                            context = requireContext(),
                            successMessage = getString(R.string.user_added_successfully)
                        )
                        viewModel.resetInputs()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorState.collectLatest {
                    if (it.isNotEmpty()) {
                        CustomDialog.showErrorDialog(
                            context = requireContext(), errorMessage = it
                        )
                        viewModel.clearErrorState()
                    }
                }
            }
        }

        clearFieldsErrorsMessages(
            valueObserver = viewModel.name,
            errorObserver = viewModel.nameError
        )
        clearFieldsErrorsMessages(
            valueObserver = viewModel.jobTitle,
            errorObserver = viewModel.jobTitleError
        )
        clearFieldsErrorsMessages(
            valueObserver = viewModel.age,
            errorObserver = viewModel.ageError
        )

        return binding.root
    }

    private fun clearFieldsErrorsMessages(
        valueObserver: MutableLiveData<String>,
        errorObserver: MutableLiveData<String>
    ) {
        valueObserver.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                errorObserver.value = ""
            }
        }
    }
}