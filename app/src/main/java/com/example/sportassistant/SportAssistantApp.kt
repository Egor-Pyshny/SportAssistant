package com.example.sportassistant

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sportassistant.di.coreModules
import com.example.sportassistant.di.dataModules
import com.example.sportassistant.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class SportAssistantApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@SportAssistantApp)
            modules(
                coreModules,
                networkModule,
                dataModules,
            )
        }
    }
}