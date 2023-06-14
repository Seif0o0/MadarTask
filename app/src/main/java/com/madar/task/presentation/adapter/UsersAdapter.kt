package com.madar.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madar.task.databinding.UserItemBinding
import com.madar.task.domain.model.User
import com.madar.task.presentation.utils.ListItemClickListener
import javax.inject.Inject

class UsersAdapter @Inject constructor() :
    PagingDataAdapter<User, UsersAdapter.ViewHolder>(UsersComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder private constructor(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    UserItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    object UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }
}