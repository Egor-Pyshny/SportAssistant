package com.example.sportassistant.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.domain.interfaces.services.UserApiService
import com.example.sportassistant.presentation.utils.CookieAddInterceptor
import com.example.sportassistant.presentation.utils.ZonedDateTimeAdapter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
val networkModule = module {
    factory { CookieAddInterceptor(get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideAuthApi(get()) }
    factory { provideUserApi(get()) }
    factory { provideCoachApi(get()) }
    single { provideRetrofit(get()) }
}

@RequiresApi(Build.VERSION_CODES.O)
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
        .create()

    return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1.0/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun provideOkHttpClient(authInterceptor: CookieAddInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideAuthApi(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)
fun provideUserApi(retrofit: Retrofit): UserApiService = retrofit.create(UserApiService::class.java)
fun provideCoachApi(retrofit: Retrofit): CoachApiService = retrofit.create(CoachApiService::class.java)
