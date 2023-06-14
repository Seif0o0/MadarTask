package com.madar.task.presentation.utils

open class ListItemClickListener<T>(val clickListener: (data: T) -> Unit) {
    fun onItemClickListener(data: T) = clickListener(data)
}