package com.runningapp.app.di

import android.content.Context
import com.runningapp.app.common.Constants
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.repository.RunRepositoryImpl
import com.runningapp.app.data.repository.AuthRepositoryImpl
import com.runningapp.app.data.repository.ChallengeRepositoryImpl
import com.runningapp.app.domain.repository.RunRepository
import com.runningapp.app.domain.repository.AuthRepository
import com.runningapp.app.domain.repository.ChallengeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunningAppServerApi(): RunningAppServerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RunningAppServerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: RunningAppServerApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRunRepository(api: RunningAppServerApi): RunRepository {
        return RunRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideChallengeRepository(api: RunningAppServerApi): ChallengeRepository {
        return ChallengeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}