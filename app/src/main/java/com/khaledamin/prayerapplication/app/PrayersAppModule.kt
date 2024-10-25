package com.khaledamin.prayerapplication.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.khaledamin.prayerapplication.data.local.PrayerDao
import com.khaledamin.prayerapplication.data.local.PrayerDatabase
import com.khaledamin.prayerapplication.data.remote.PrayerApi
import com.khaledamin.prayerapplication.data.repository.PrayersRepoImpl
import com.khaledamin.prayerapplication.domain.repository.PrayersRepo
import com.khaledamin.prayerapplication.utils.Constants
import com.khaledamin.prayerapplication.utils.NetworkState
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
class PrayersAppModule : Application() {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun providePrayersApi(retrofit: Retrofit): PrayerApi = retrofit.create(PrayerApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory).build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providePrayersRepo(prayerApi: PrayerApi, prayerDao: PrayerDao): PrayersRepo =
        PrayersRepoImpl(prayerApi, prayerDao)

    @Provides
    @Singleton
    fun providePrayerDatabase(@ApplicationContext context: Context): PrayerDatabase =
        Room.databaseBuilder(context, PrayerDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePrayerDao(prayerDatabase: PrayerDatabase): PrayerDao = prayerDatabase.prayerDao()

    @Provides
    @Singleton
    fun provideNetworkState(@ApplicationContext context: Context): NetworkState =
        NetworkState(context)
}