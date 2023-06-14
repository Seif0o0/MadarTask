package com.madar.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madar.task.databinding.LoadMoreProgressViewBinding

class UserLoadStateAdapter : LoadStateAdapter<UserLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState is LoadState.Loading)
    }

    class ViewHolder private constructor(private val binding: LoadMoreProgressViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            loadingStatus: Boolean
        ) {
            binding.loadingStatus = loadingStatus
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LoadMoreProgressViewBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }
}