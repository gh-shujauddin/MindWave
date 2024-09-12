package com.qadri81.mindwave.di

import com.qadri81.mindwave.api.AuthInterceptor
import com.qadri81.mindwave.api.NoteApi
import com.qadri81.mindwave.api.UserApi
import com.qadri81.mindwave.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)

    @Singleton
    @Provides
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): UserApi =
        retrofitBuilder.build().create(UserApi::class.java)

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesNoteApi(okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder): NoteApi =
        retrofitBuilder
            .client(okHttpClient)
            .build().create(NoteApi::class.java)
}