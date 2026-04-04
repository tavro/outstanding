package com.tavro.outstanding.di

import com.tavro.outstanding.navigation.Navigator
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() }
}

val appModule = module {
    includes(navigationModule)
}
