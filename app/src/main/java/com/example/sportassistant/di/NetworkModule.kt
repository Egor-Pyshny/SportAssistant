package com.example.sportassistant.di

import com.example.sportassistant.domain.interfaces.services.AnthropometricParamsApiService
import com.example.sportassistant.domain.interfaces.services.AuthApiService
import com.example.sportassistant.domain.interfaces.services.CoachApiService
import com.example.sportassistant.domain.interfaces.services.CompetitionApiService
import com.example.sportassistant.domain.interfaces.services.ComprehensiveExaminationApiService
import com.example.sportassistant.domain.interfaces.services.MedExaminationApiService
import com.example.sportassistant.domain.interfaces.services.NotesApiService
import com.example.sportassistant.domain.interfaces.services.OFPResultsApiService
import com.example.sportassistant.domain.interfaces.services.SFPResultsApiService
import com.example.sportassistant.domain.interfaces.services.TrainingCampsApiService
import com.example.sportassistant.domain.interfaces.services.UserApiService
import com.example.sportassistant.presentation.utils.CookieAddInterceptor
import com.example.sportassistant.presentation.utils.LocalDateAdapter
import com.example.sportassistant.presentation.utils.LocalDateTimeAdapter
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime

val networkModule = module {
    factory { CookieAddInterceptor(get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideAuthApi(get()) }
    factory { provideUserApi(get()) }
    factory { provideCoachApi(get()) }
    factory { provideCompetitionApi(get()) }
    factory { provideTrainingCampsApi(get()) }
    factory { provideOFPResultsApi(get()) }
    factory { provideSFPResultsApi(get()) }
    factory { provideAnthropometricParamsApi(get()) }
    factory { provideNotesApi(get()) }
    factory { provideComprehensiveExamination(get()) }
    factory { provideMedExamination(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
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
fun provideCompetitionApi(retrofit: Retrofit): CompetitionApiService = retrofit.create(CompetitionApiService::class.java)
fun provideTrainingCampsApi(retrofit: Retrofit): TrainingCampsApiService = retrofit.create(TrainingCampsApiService::class.java)
fun provideOFPResultsApi(retrofit: Retrofit): OFPResultsApiService = retrofit.create(OFPResultsApiService::class.java)
fun provideSFPResultsApi(retrofit: Retrofit): SFPResultsApiService = retrofit.create(SFPResultsApiService::class.java)
fun provideAnthropometricParamsApi(retrofit: Retrofit): AnthropometricParamsApiService = retrofit.create(AnthropometricParamsApiService::class.java)
fun provideNotesApi(retrofit: Retrofit): NotesApiService = retrofit.create(NotesApiService::class.java)
fun provideComprehensiveExamination(retrofit: Retrofit): ComprehensiveExaminationApiService = retrofit.create(ComprehensiveExaminationApiService::class.java)
fun provideMedExamination(retrofit: Retrofit): MedExaminationApiService = retrofit.create(MedExaminationApiService::class.java)
