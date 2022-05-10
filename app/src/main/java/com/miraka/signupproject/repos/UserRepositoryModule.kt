package com.miraka.signupproject.repos

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 *  Module providing shared userRepository instance
 *
 */
@InstallIn(SingletonComponent::class)
@Module
object UserRepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepositoryDispatcher() = Dispatchers.IO


    @Singleton
    @Provides
    fun provideUserRepository(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): UserRepository {
        return UserRepository(
            dispatcher
        )
    }
}