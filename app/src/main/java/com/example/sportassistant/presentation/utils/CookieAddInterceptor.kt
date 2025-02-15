package com.example.sportassistant.presentation.utils

import com.example.sportassistant.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class CookieAddInterceptor(
    private val preferences: UserPreferencesRepository,
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        var request: Request = chain.request()

        val sid = runBlocking{
            preferences.getSID().first()
        }

        request = request.newBuilder()
            .addHeader("Cookie", "sid=$sid")
            .build()

        val response: Response = chain.proceed(request)
        return response
    }
}