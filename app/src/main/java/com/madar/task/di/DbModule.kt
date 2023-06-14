package com.madar.task.di

import android.content.Context
import androidx.room.Room
import com.madar.task.data.db.UserDao
import com.madar.task.data.db.UsersDatabase
import com.madar.task.utils.Constants.USERS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context = context, klass = UsersDatabase::class.java, name = USERS_DATABASE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(db: UsersDatabase) = db.UserDao()
}