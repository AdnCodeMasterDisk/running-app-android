package com.runningapp.app.di

import android.content.Context
import com.runningapp.app.common.Constants
import com.runningapp.app.data.UserPreferences
import com.runningapp.app.data.remote.RunningAppServerApi
import com.runningapp.app.data.repository.UserRepositoryImpl
import com.runningapp.app.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
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
    fun provideUserRepository(api: RunningAppServerApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}