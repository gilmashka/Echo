package com.echo.core.network.di

import com.echo.core.network.EchoApi
import com.echo.core.network.storage.AuthStorage
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    companion object{
        private const val BASE_URL = "http://10.0.2.2:8080/api/"

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authStorage: AuthStorage): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val creds = authStorage.getCredentials()

                val requestBuilder = original.newBuilder()
                if (creds != null && !original.url.toString().contains("/register")) {
                    val basic = okhttp3.Credentials.basic(creds.first, creds.second)
                    requestBuilder.header("Authorization", basic)
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideEchoApi(retrofit: Retrofit): EchoApi {
        return retrofit.create(EchoApi::class.java)
    }
}