package com.example.sportassistant

import android.app.Application
import com.example.sportassistant.di.coreModules
import com.example.sportassistant.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
                dataModules,
            )
        }
    }
}