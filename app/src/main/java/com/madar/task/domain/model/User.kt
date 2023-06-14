package com.madar.task.domain.model

data class User(
    val userId: Int,
    val name: String,
    val age: Int,
    val jobTitle: String,
    val gender: Int /* 0-> male, 1-> female */
)