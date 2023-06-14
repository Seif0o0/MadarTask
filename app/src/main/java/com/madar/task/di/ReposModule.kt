package com.madar.task.di

import com.madar.task.data.repository.*
import com.madar.task.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAddUserRepo(repoImpl: AddUserRepoImpl): AddUserRepo

    @Binds
    @ViewModelScoped
    abstract fun provideUsersRepo(repoImpl: UsersRepoImpl): UsersRepo
}