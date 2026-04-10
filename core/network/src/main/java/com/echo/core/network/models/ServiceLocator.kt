package com.echo.core.network.models

import com.echo.core.network.EchoApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceLocator {

    private const val BASE_URL = "http://10.0.2"

    var userNickname = ""
    var userPassword = ""
    var userId: Long = 0

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor {
            chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            if(userNickname.isNotEmpty() && userPassword.isNotEmpty()){
                val credentials = okhttp3.Credentials.basic(userNickname, userPassword)
                requestBuilder.header("Authorization", credentials)
            }

            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val echoApi = retrofit.create(EchoApi::class.java)
}