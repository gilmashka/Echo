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

    companion object {
        private const val BASE_URL = "http://192.168.0.200:8080/api/"
        const val UPLOADS_URL = "http://192.168.0.200:8080/uploads/users/"
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

                android.util.Log.d("OkHttp", "URL: ${original.url}")
                android.util.Log.d("OkHttp", "Has creds: ${creds != null}")

                val requestBuilder = original.newBuilder()
                if (creds != null
                    && !original.url.toString().contains("/register")
                    && !original.url.toString().contains("/login")) {
                    val basic = okhttp3.Credentials.basic(creds.first, creds.second)
                    android.util.Log.d("OkHttp", "Adding Basic Auth: $basic")
                    requestBuilder.header("Authorization", basic)
                } else {
                    android.util.Log.d("OkHttp", "Skipping Basic Auth")
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