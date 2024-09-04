package com.qadri81.mindwave.di

import com.qadri81.mindwave.api.UserApi
import com.qadri81.mindwave.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)
}