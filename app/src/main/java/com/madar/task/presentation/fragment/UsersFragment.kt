package com.madar.task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.madar.task.databinding.FragmentUsersBinding
import com.madar.task.presentation.adapter.UserLoadStateAdapter
import com.madar.task.presentation.adapter.UsersAdapter
import com.madar.task.presentation.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var binding: FragmentUsersBinding

    @Inject
    lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        usersAdapter.addLoadStateListener { combinedLoadStates ->
            viewModel.handleLoadState(
                combinedLoadStates = combinedLoadStates,
                itemCount = usersAdapter.itemCount
            )
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter()
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.users.collectLatest {
                    usersAdapter.submitData(it)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

        return binding.root
    }
}