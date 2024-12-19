package com.example.sportassistant.di

import com.example.sportassistant.domain.model.AppDispatchers
import org.koin.dsl.module

val coreModules = module {
    single { AppDispatchers() }
}