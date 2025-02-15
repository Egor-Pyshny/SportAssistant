package com.example.sportassistant.di

import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.domain.interfaces.services.UserApiService
import com.example.sportassistant.presentation.utils.CookieAddInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { CookieAddInterceptor(get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideAuthApi(get()) }
    factory { provideUserApi(get()) }
    factory { provideCoachApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8000/api/v1.0/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: CookieAddInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideAuthApi(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)
fun provideUserApi(retrofit: Retrofit): UserApiService = retrofit.create(UserApiService::class.java)
fun provideCoachApi(retrofit: Retrofit): CoachApiService = retrofit.create(CoachApiService::class.java)
