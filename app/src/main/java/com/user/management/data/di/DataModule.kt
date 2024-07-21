package com.user.management.data.di

import android.content.Context
import androidx.room.Room
import com.user.management.BuildConfig
import com.user.management.data.GithubApi
import com.user.management.data.models.dao.UserDAO
import com.user.management.data.models.dao.UserDatabase
import com.user.management.data.repositories.LocalUserRepositoryImpl
import com.user.management.data.repositories.NetworkUserRepositoryImpl
import com.user.management.domain.repositories.LocalUserRepository
import com.user.management.domain.repositories.NetworkUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNetworkApi(): GithubApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(logging)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .baseUrl(BuildConfig.GITHUB_API_URL).build().create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkUserRepository(api: GithubApi): NetworkUserRepository = NetworkUserRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideLocalUserRepository(userDAO: UserDAO): LocalUserRepository = LocalUserRepositoryImpl(userDAO)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun userDao(database: UserDatabase): UserDAO {
        return database.userDao()
    }
}